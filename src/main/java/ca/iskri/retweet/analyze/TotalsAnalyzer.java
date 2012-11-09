package ca.iskri.retweet.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.iskri.retweet.Util;
import ca.iskri.retweet.model.RetweetDateUserComparator;
import ca.iskri.retweet.model.RunningTotals;
import ca.iskri.retweet.model.TwitterUser;


public class TotalsAnalyzer {
	
	public static void main(String[] args) throws Exception {
		TotalsAnalyzer analyzer = new TotalsAnalyzer();
		analyzer.analyze();
	}
	
	private void analyze() throws Exception {
		List<TwitterUser> twitterUsers = Util.readTwitterUsers("users-edges.txt");
		Collections.sort(twitterUsers, new RetweetDateUserComparator());
		List<RunningTotals> totals = new ArrayList<RunningTotals>();
		Integer totalRetweeters = 0;
		Integer totalFollowers = 0;
		for (TwitterUser twitterUser : twitterUsers) {
			totalRetweeters++;
			totalFollowers += twitterUser.getFollowersCount();
			totals.add(new RunningTotals(twitterUser.getRetweetDate(), totalRetweeters, totalFollowers));
		}
		Util.writeCsv(totals, "totals.csv");
	}

}
