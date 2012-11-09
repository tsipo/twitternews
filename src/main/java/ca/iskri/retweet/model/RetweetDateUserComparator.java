package ca.iskri.retweet.model;

import java.util.Comparator;

public class RetweetDateUserComparator implements Comparator<TwitterUser> {

	@Override
	public int compare(TwitterUser user0, TwitterUser user1) {
		return user0.getRetweetDate().compareTo(user1.getRetweetDate());
	}

}
