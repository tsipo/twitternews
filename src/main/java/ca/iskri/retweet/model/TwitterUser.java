package ca.iskri.retweet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.User;

public class TwitterUser implements Comparable<TwitterUser> {
	
	private Long id;
	private String screenName;
	private Integer friendsCount;
	private Integer followersCount;
	private Date retweetDate = null;
	private List<Long> friendsIds = new ArrayList<Long>();
	
	public TwitterUser(Long id, String screenName, Integer followersCount, 
		Integer friendsCount, Date retweetDate) {
		this.id = id;
		this.screenName = screenName;
		this.friendsCount = friendsCount;
		this.followersCount = followersCount;
		this.retweetDate = retweetDate;
	}

	public TwitterUser(User user, Date retweetDate) {
		this(user.getId(), user.getScreenName(), user.getFriendsCount(), 
			user.getFollowersCount(), retweetDate);
	}

	public TwitterUser(String s) {
		String[] values = s.split(" ");
		this.retweetDate = new Date(new Long(values[0]));
		this.id = new Long(values[1]);
		this.screenName = values[2];
		this.friendsCount = new Integer(values[3]);
		this.followersCount = new Integer(values[4]);
		for (int i = 5; i < values.length; i++) {
			this.friendsIds.add(new Long(values[i]));
		}
	}
	
	public void addFriends(List<Long> ids) {
		friendsIds.addAll(ids);
	}
	
	public Long getId() {
		return id;
	}

	public String getScreenName() {
		return screenName;
	}

	public Integer getFriendsCount() {
		return friendsCount;
	}

	public Integer getFollowersCount() {
		return followersCount;
	}

	public Date getRetweetDate() {
		return retweetDate;
	}

	public List<Long> getFriendsIds() {
		return friendsIds;
	}

	// This natural comparison method is to efficiently find users by Ids
	@Override
	public int compareTo(TwitterUser other) {
		return this.getId().compareTo(other.getId());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getRetweetDate().getTime()).append(" ").
			append(getId()).append(" ").append(getScreenName()).append(" ").
			append(getFriendsCount()).append(" ").append(getFollowersCount());
		for (Long friendId : friendsIds) {
			sb.append(" ").append(friendId);
		}
		return sb.toString();
	}

}
