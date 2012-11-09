package ca.iskri.retweet.collect;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import ca.iskri.retweet.Util;
import ca.iskri.retweet.model.Retweet;
import ca.iskri.retweet.otter.OtterEnvelope;
import ca.iskri.retweet.otter.OtterGateway;
import ca.iskri.retweet.otter.OtterResponse;
import ca.iskri.retweet.otter.OtterStatus;

public class RetweetAggregator {
	
	public static final Integer maxTotal = 500; 

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("usage: java RetweetAggreator.class status_id");
			return;
		}
		
		Long statusId = new Long(args[0]);
		
		RetweetAggregator aggregator = new RetweetAggregator();
		aggregator.collect(statusId);
	}

	private void collect(Long statusId) throws Exception {
		Twitter twitter = new TwitterFactory().getInstance();

		Status status = twitter.showStatus(statusId);
		Date createdAt = status.getCreatedAt();
		String userScreenName = status.getUser().getScreenName();

		List<Retweet> retweets = new ArrayList<Retweet>();
		// First of all, create a "retweet" for the original tweet
		retweets.add(new Retweet(createdAt.getTime(), userScreenName));
		
		OtterGateway gateway = new OtterGateway();

		long minTime = Util.toSeconds(createdAt);
		long now = Util.toSeconds(new Date());
		long originalDelta = 3600L;
		
		while (minTime < now) {
			long delta = originalDelta;
			long maxTime = 0L;
			Boolean allCaptured = null;
			Integer page = 1;
			OtterEnvelope envelope = null;
			do {
				maxTime = minTime + delta;
				envelope = gateway.trackbacks(userScreenName, statusId, minTime, maxTime, page, 100, true);
				allCaptured = envelope.getResponse().getTotal() < maxTotal;
				if (!allCaptured) {
					delta /= 2L;
				}
			} while (!allCaptured);
			
			addAll(retweets, envelope);
			while (hasMore(envelope)) {
				page++;
				System.out.println(page);
				envelope = gateway.trackbacks(userScreenName, statusId, minTime, maxTime, page, 100, true);
				addAll(retweets, envelope);
			}
			minTime = maxTime;			
		}
		
		Util.write(retweets, "rewteets.txt");
	}
	
	private void addAll(List<Retweet> retweets, OtterEnvelope envelope) {
		List<OtterStatus> statuses = envelope.getResponse().getList();
		for (OtterStatus status : statuses) {
			retweets.add(new Retweet(status));
		}
	}
	
	private Boolean hasMore(OtterEnvelope envelope) {
		OtterResponse response = envelope.getResponse();
		return (response.getLast_offset() < response.getTotal());
	}
	
	
}
