package ca.iskri.retweet.collect;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import ca.iskri.retweet.Util;
import ca.iskri.retweet.model.Retweet;
import ca.iskri.retweet.model.TwitterUser;

public class UserAggregator {

	private static final Integer lookupFrame = 100;
	
	public static void main(String[] args) throws Exception {
		UserAggregator aggregator = new UserAggregator();
		aggregator.collect();
	}
	
	private void collect() throws Exception {
		List<Retweet> retweets = Util.readRetweets("rewteets.txt");
		List<TwitterUser> twitterUsers = new ArrayList<TwitterUser>();
		Twitter twitter = new TwitterFactory().getInstance();
		
		Integer size = retweets.size();
		Integer base = 0;
		while (base < size) {
			SortedMap<String, Date> retweetTimes = new TreeMap<String, Date>();
			Integer n = Math.min((size - base), lookupFrame);
			String[] names = new String[n];
			for (int i = 0; i < n; i++) {
				Retweet retweet = retweets.get(base + i);
				names[i] = retweet.getUserScreenName().toLowerCase();
				retweetTimes.put(retweet.getUserScreenName().toLowerCase(), retweet.getCreatedAt());
			}
			// the repeated call to lookupUsers is subject to 
			// "Twitter is over capacity" HTML error pages, sometimes 20%-30% of the requests.
			// hence the exception handling (repeating the same request) + the sleep
			try {
				ResponseList<User> users = twitter.lookupUsers(names);
				for (User user : users) {
					Date date = retweetTimes.get(user.getScreenName().toLowerCase());
					if (date == null) {
						System.err.println("not found date for user " + user.getScreenName());
					} else {
						twitterUsers.add(new TwitterUser(user, date));
					}
				}
				base += n;
			} catch (TwitterException ex) {
				System.err.println(ex.getMessage());
			}
			System.out.println(base);
			Thread.sleep(5000);
		}
		
		Util.write(twitterUsers, "users.txt");
	}
	
}
