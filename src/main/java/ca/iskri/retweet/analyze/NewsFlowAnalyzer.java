package ca.iskri.retweet.analyze;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.ext.GmlExporter;
import org.jgrapht.ext.IntegerEdgeNameProvider;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import ca.iskri.retweet.Util;
import ca.iskri.retweet.analyze.gml.TwitterUserIdProvider;
import ca.iskri.retweet.analyze.gml.TwitterUserLabelProvider;
import ca.iskri.retweet.analyze.gml.WeightedEdgeLabelProvider;
import ca.iskri.retweet.model.InfluentialTwitterUser;
import ca.iskri.retweet.model.RetweetDateUserComparator;
import ca.iskri.retweet.model.TwitterUser;

public class NewsFlowAnalyzer {
	
	public static void main(String[] args) throws Exception {
		NewsFlowAnalyzer analyzer = new NewsFlowAnalyzer();
		analyzer.analyze();
	}
	
	private void analyze() throws Exception {
		List<TwitterUser> twitterUsers = Util.readTwitterUsers("users-edges.txt");
		Collections.sort(twitterUsers, new RetweetDateUserComparator());

		// the sorted map is to efficiently find users by their id
		SortedMap<Long, TwitterUser> referenceUsers = new TreeMap<Long, TwitterUser>();
		for (TwitterUser user : twitterUsers) {
			referenceUsers.put(user.getId(), user);
		}
		
		// create the graph
		DefaultDirectedWeightedGraph<TwitterUser, DefaultWeightedEdge> graph =
			new DefaultDirectedWeightedGraph<TwitterUser, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		// add the ORIGIN node
		TwitterUser origin = twitterUsers.get(0);
		graph.addVertex(origin);
		
		// add the fictive UNKNOWN node - unexisting Twitter user, same retweetDate as origin
		TwitterUser unknown = new TwitterUser(-1L, "unknown", 0, 0, origin.getRetweetDate());
		graph.addVertex(unknown);
		// add an edge (so that we have a path from origin to all other users) - with weight 0.0
		addEdge(graph, origin, unknown);
		
		// add all other nodes and determine their edges
		for (TwitterUser twitterUser : twitterUsers) {
			// ignore origin as this node is already in the graph
			if (!twitterUser.equals(origin)) {
				graph.addVertex(twitterUser);
				TwitterUser source = unknown; // default source node for all edges when we cannot locate the real source
				Long weight = weight(unknown, twitterUser);
				List<Long> friendsIds = twitterUser.getFriendsIds();
				for (Long id : friendsIds) {
					TwitterUser other = referenceUsers.get(id);
					Long difference = weight(other, twitterUser);
					if ((difference > 0) && (difference < weight)) {
						source = other;
						weight = difference;
					}
				}
				addEdge(graph, source, twitterUser);
			}
		}
		
		// export and analyze
		exportToGml(graph, "retweet.gml");
		calculateInfluence(graph, "influential-users.csv");
		calculateShortestPaths(graph, origin, unknown, "paths.txt");
	}
	
	private void addEdge(DefaultDirectedWeightedGraph<TwitterUser, DefaultWeightedEdge> graph,
		TwitterUser source, TwitterUser target) {
		
		DefaultWeightedEdge edge = graph.addEdge(source, target);
		Double weight = new Double(weight(source, target));
		graph.setEdgeWeight(edge, weight);
	}
	
	private Long weight(TwitterUser source, TwitterUser target) {
		return (target.getRetweetDate().getTime() - source.getRetweetDate().getTime());
	}
	
	private void exportToGml(DefaultDirectedWeightedGraph<TwitterUser, DefaultWeightedEdge> graph, String fileName) throws Exception {
		GmlExporter<TwitterUser, DefaultWeightedEdge> exporter = 
			new GmlExporter<TwitterUser, DefaultWeightedEdge>
				(new TwitterUserIdProvider(), new TwitterUserLabelProvider(),
				 new IntegerEdgeNameProvider<DefaultWeightedEdge>(), new WeightedEdgeLabelProvider(graph));
		exporter.setPrintLabels(GmlExporter.PRINT_EDGE_VERTEX_LABELS);
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		exporter.export(writer, graph);
	}
	
	private void calculateInfluence(DefaultDirectedWeightedGraph<TwitterUser, DefaultWeightedEdge> graph, String fileName) throws Exception {
		Set<TwitterUser> twitterUsers = graph.vertexSet();
		List<InfluentialTwitterUser> influentialUsers = new ArrayList<InfluentialTwitterUser>();
		for (TwitterUser twitterUser : twitterUsers) {
			influentialUsers.add(new InfluentialTwitterUser(twitterUser, graph.outDegreeOf(twitterUser)));
		}
		Collections.sort(influentialUsers, Collections.reverseOrder());
		Util.writeCsv(influentialUsers, fileName);
	}
	
	private void calculateShortestPaths(DefaultDirectedWeightedGraph<TwitterUser, DefaultWeightedEdge> graph, 
		TwitterUser origin, TwitterUser unknown, String fileName) throws Exception {

		Double averageDuration = 0.0;
		Double averageChainLength = 0.0;
		
		Double maxDuration = 0.0;
		Integer maxChainLength = 0;	

		for (TwitterUser twitterUser : graph.vertexSet()) {
			if ((!twitterUser.equals(origin)) && (!twitterUser.equals(unknown))) {
				DijkstraShortestPath<TwitterUser, DefaultWeightedEdge> shortestPath =
					new DijkstraShortestPath<TwitterUser, DefaultWeightedEdge>(graph, origin, twitterUser);
				Double duration = shortestPath.getPathLength();
				averageDuration += duration;
				if (duration > maxDuration) {
					maxDuration = duration;
				}
				// the length of the chain is the unweighted length of the path (no. of hops)
				List<DefaultWeightedEdge> edges = shortestPath.getPathEdgeList();
				Integer chainLength = shortestPath.getPathEdgeList().size();
				if (edges.get(0).equals(graph.getEdge(origin, unknown))) {
					chainLength--;
				}
				averageChainLength += chainLength;
				if (chainLength > maxChainLength) {
					maxChainLength = chainLength;
				}
			}
		}
		
		Integer size = graph.vertexSet().size() - 2;

		averageChainLength /= size;
		averageDuration /= size;
		averageDuration /= 1000; // in seconds
		maxDuration /= 1000; // in seconds

		List<String> output = new ArrayList<String>();
		output.add("average duration (seconds): " + averageDuration);
		output.add("max duration (seconds): " + maxDuration);
		output.add("average chain length: " + averageChainLength);
		output.add("max chain length (diameter): " + maxChainLength);		
		
		Util.write(output, fileName);
	}
	
}
