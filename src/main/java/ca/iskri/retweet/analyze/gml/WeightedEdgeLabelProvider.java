package ca.iskri.retweet.analyze.gml;

import org.jgrapht.ext.EdgeNameProvider;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class WeightedEdgeLabelProvider implements EdgeNameProvider<DefaultWeightedEdge> {

	private DefaultDirectedWeightedGraph<? extends Object, DefaultWeightedEdge> graph;
		
	public WeightedEdgeLabelProvider(DefaultDirectedWeightedGraph<? extends Object, DefaultWeightedEdge> graph) {
		this.graph = graph;
	}
	
	@Override
	public String getEdgeName(DefaultWeightedEdge e) {
		// here we return the weight ("value"), which is far more important than the label;
		// we use hashCode() as the label, as we have no access to the id in this name provider.
		// As the entire call is wrapped in quoted() we have to make sure that the 
		// first element has end-quote and the last one has start-quote
		StringBuilder sb = new StringBuilder();
		sb.append(e.hashCode()).append("\"\n").
			append("\t\tvalue \"").append(graph.getEdgeWeight(e));
		return sb.toString();
	}

}
