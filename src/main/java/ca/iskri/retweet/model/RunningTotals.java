package ca.iskri.retweet.model;

import java.util.Date;

import ca.iskri.retweet.Util;

public class RunningTotals implements Comparable<RunningTotals>, CsvRecord {
	
	private Date retweetDate;
	private Integer retweeters;
	private Integer followers;
	
	public RunningTotals() {
	}
	
	public RunningTotals(Date retweetDate, Integer retweeters, Integer followers) {
		setRetweetDate(retweetDate);
		setRetweeters(retweeters);
		setFollowers(followers);
	}
	
	public Date getRetweetDate() {
		return retweetDate;
	}

	public void setRetweetDate(Date retweetDate) {
		this.retweetDate = retweetDate;
	}

	public Integer getRetweeters() {
		return retweeters;
	}
	
	public void setRetweeters(Integer retweeters) {
		this.retweeters = retweeters;
	}
	
	public Integer getFollowers() {
		return followers;
	}
	
	public void setFollowers(Integer followers) {
		this.followers = followers;
	}

	@Override
	public String[] toStringArray() {
		return new String[] {Util.format(getRetweetDate()), getRetweeters().toString(), getFollowers().toString()};
	}

	@Override
	public int compareTo(RunningTotals other) {
		return this.getRetweetDate().compareTo(other.getRetweetDate());
	}
	
	

}
