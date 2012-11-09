package ca.iskri.retweet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import ca.iskri.retweet.model.CsvRecord;
import ca.iskri.retweet.model.Retweet;
import ca.iskri.retweet.model.TwitterUser;

public abstract class Util {

	private static final DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	// Topsy uses seconds, whereas Date is in milliseconds
	public static Long toSeconds(Date date) {
		return date.getTime() / 1000L;
	}

	// Topsy uses seconds, whereas Date is in milliseconds
	public static Date toDate(Long l) {
		return new Date(l * 1000L);
	}

	public static void write(List<? extends Object> list, String fileName) throws Exception {
		write(list, 0, list.size(), fileName);
	}

	public static void write(List<? extends Object> list, Integer base, Integer max, String fileName) throws Exception {
		FileWriter fstream = new FileWriter(fileName);
		BufferedWriter out = new BufferedWriter(fstream);
		for (int i = base; i < max; i++) {
			write(out, list.get(i));
		}
		out.close();
	}
	
	public static void writeCsv(Collection<? extends CsvRecord> csvRecords, String fileName) throws Exception {
		FileWriter fstream = new FileWriter(fileName);
		BufferedWriter out = new BufferedWriter(fstream);
		for (CsvRecord record : csvRecords) {
			String[] values = record.toStringArray();
			StringBuilder sb = new StringBuilder();
			for (String value : values) {
				if (sb.length() > 0) {
					sb.append(',');
				}
				sb.append(value);
			}
			write(out, sb);
		}
		out.close();
	}
	
	public static void write(BufferedWriter out, Object object) throws Exception {
		out.write(object.toString());
		out.newLine();
	}
	
	public static List<Retweet> readRetweets(String fileName) throws Exception {
		List<Retweet> retweets = new ArrayList<Retweet>();
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
			retweets.add(new Retweet(strLine));
		}
		in.close();
		return retweets;
	}

	public static List<TwitterUser> readTwitterUsers(String fileName) throws Exception {
		List<TwitterUser> twitterUsers = new ArrayList<TwitterUser>();
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
			twitterUsers.add(new TwitterUser(strLine));
		}
		in.close();
		return twitterUsers;
	}

	public static String format(Date date) {
		return format.format(date);		
	}
	
	public static String quoted(String s) {
		return "\"" + s + "\"";
	}
}
