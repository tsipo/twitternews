package ca.iskri.retweet.analyze.gml;

import org.jgrapht.ext.VertexNameProvider;

import ca.iskri.retweet.Util;
import ca.iskri.retweet.model.TwitterUser;

public class TwitterUserLabelProvider implements VertexNameProvider<TwitterUser> {

	@Override
	public String getVertexName(TwitterUser user) {
		// here we return the entire user information (not only the label!)
		// and as the entire call is wrapped in quoted() we have to make sure that the 
		// first element has end-quote and the last one has start-quote
		StringBuilder sb = new StringBuilder();
		sb.append(user.getScreenName()).append("\"\n").
			append("\t\tfriendsCount ").append(Util.quoted(user.getFriendsCount().toString())).append('\n').
			append("\t\tfollowersCount ").append(Util.quoted(user.getFollowersCount().toString())).append('\n').
			append("\t\tretweetDate \"").append(Util.format(user.getRetweetDate()));
		return sb.toString();
	}

}
