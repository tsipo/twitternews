package ca.iskri.retweet.model;

import java.util.Date;

import ca.iskri.retweet.Util;
import ca.iskri.retweet.otter.OtterStatus;

public class Retweet {
	
	private Date createdAt = null;
	private String userScreenName = null;
	
	public Retweet(Long date, String userScreenName) {
		this.createdAt = new Date(date);
		this.userScreenName = userScreenName;
	}

	public Retweet(OtterStatus status) {
		this.createdAt = Util.toDate(status.getDate());
		this.userScreenName = status.getAuthor().getNick();
	}
	
	public Retweet(String s) {
		String[] values = s.split(" ");
		this.createdAt = new Date(new Long(values[0]));
		this.userScreenName = values[1];
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getUserScreenName() {
		return userScreenName;
	}
	
	@Override
	public String toString() {
		return "" + getCreatedAt().getTime() + " " + getUserScreenName();
	}

}
