package ca.iskri.retweet.model;

import ca.iskri.retweet.Util;

public class InfluentialTwitterUser implements CsvRecord, Comparable<InfluentialTwitterUser> {

	private TwitterUser user;
	private Integer retweetersCount;
	
	public InfluentialTwitterUser(TwitterUser user, Integer retweetersCount) {
		this.user = user;
		this.retweetersCount = retweetersCount;
	}

	public TwitterUser getUser() {
		return user;
	}

	public Integer getRetweetersCount() {
		return retweetersCount;
	}


	@Override
	public String[] toStringArray() {
		return new String[] {getUser().getScreenName(), getRetweetersCount().toString(), 
			getUser().getFollowersCount().toString(), Util.format(getUser().getRetweetDate())};
		}

	@Override
	public int compareTo(InfluentialTwitterUser other) {
		return this.getRetweetersCount().compareTo(other.getRetweetersCount());
	}
}
