package ca.iskri.retweet.analyze.gml;

import org.jgrapht.ext.VertexNameProvider;

import ca.iskri.retweet.model.TwitterUser;

public class TwitterUserIdProvider implements VertexNameProvider<TwitterUser> {

	@Override
	public String getVertexName(TwitterUser user) {
		return user.getId().toString();
	}

}
