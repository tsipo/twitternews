package ca.iskri.retweet.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import ca.iskri.retweet.Util;
import ca.iskri.retweet.model.TwitterUser;

public class FriendsAggregator {
	
	public static void main(String[] args) throws Exception {
		List<TwitterUser> twitterUsers = Util.readTwitterUsers("users.txt");

		Integer base = (args.length > 0) ? new Integer(args[0]) : 0;
		Integer max = (args.length > 1) ? (base + new Integer(args[1])) : twitterUsers.size();

		FriendsAggregator aggregator = new FriendsAggregator();
		aggregator.collect(twitterUsers, base, max);
	}
	
	private void collect(List<TwitterUser> twitterUsers, Integer base, Integer max) throws Exception {
		SortedSet<Long> twitterUsersIds = new TreeSet<Long>();
		for (TwitterUser twitterUser : twitterUsers) {
			twitterUsersIds.add(twitterUser.getId());
		}
		Twitter twitter = new TwitterFactory().getInstance();
		
		// better to aggregate friends as some popular users have millions of followers,
		// but nobody follows more than a few (tens of) thousands
		for (int i = base; i < max; i++) {
			TwitterUser user = twitterUsers.get(i);
			List<Long> friendsIds = new ArrayList<Long>();
			IDs ids = null;
			long cursor = IDs.START;
			do {
				Boolean success = false;
				do {
					try {
						ids = twitter.getFriendsIDs(user.getId(), cursor);
						success = true;
					} catch (TwitterException ex) {
						System.err.println(ex.getErrorMessage());
						if (ex.getStatusCode() == 401) {
							// not authorized
							success = true;
						}
					} finally {
						Thread.sleep(10000);
					}
				} while (!success);
				if (ids != null) {
					for (Long id : ids.getIDs()) {
						if (twitterUsersIds.contains(id)) {
							friendsIds.add(id);
						}
					}
					if (ids.hasNext()) {
						cursor = ids.getNextCursor();
					}
				}
			} while ((ids != null) && (ids.hasNext()));
			user.addFriends(friendsIds);
			System.out.println(i);
		}

		Util.write(twitterUsers, base, max, base.toString() + "users-edges.txt");
		
	}
	
}
