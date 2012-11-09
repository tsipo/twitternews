package ca.iskri.retweet.otter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class OtterGateway {

	private String urlString = null;
	private Boolean isSecureConnection = null;
	private Integer timeout = null;

	public OtterGateway() throws Exception {
		this("otter.properties");
	}
	
	public OtterGateway(String propertyFileName) throws Exception{
		InputStream is = getClass().getClassLoader().getResourceAsStream(propertyFileName);
		
		Properties properties = new Properties();
		properties.load(is);
		String baseUrl = properties.getProperty("otter.baseurl");
		String apiKey = properties.getProperty("otter.apikey");
		Integer timeout = new Integer(properties.getProperty("otter.timeout"));
		
		init(baseUrl, timeout, apiKey);
	}
	
	public OtterGateway(String baseUrl, Integer timeout, String apiKey) throws Exception {
		init(baseUrl, timeout, apiKey);
	}
	
	private void init(String baseUrl, Integer timeout, String apiKey) throws Exception {
		this.timeout = timeout;
		this.urlString = baseUrl + "?apikey=" + apiKey;
		URL url = new URL(urlString);
		String protocol = url.getProtocol();
		if (protocol.equalsIgnoreCase("http")) {
			isSecureConnection = Boolean.FALSE;
		} else if (protocol.equalsIgnoreCase("https")) {
			isSecureConnection = Boolean.TRUE;
		}		
	}

	public OtterEnvelope trackbacks(String twitterUserName, Long statusId, Long minTime, Long maxTime, 
			Integer page, Integer count, Boolean retweetsOnly) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(urlString).
			append("&url=http://twitter.com/").append(twitterUserName).append("/status/").append(statusId).
			append("&mintime=").append(minTime).append("&maxtime=").append(maxTime).
			append("&page=").append(page).append("&perpage=").append(count).
			append("&sort_method=-date");
		if (retweetsOnly) {
			sb.append("&tracktype=retweet__native");
		}
		URL url = new URL(sb.toString());
		HttpURLConnection connection = null;
		InputStream in = null;
		OtterEnvelope envelope = null;
		try {
			URLConnection urlConnection = url.openConnection();
			connection = (isSecureConnection) ?
				(HttpsURLConnection) urlConnection : (HttpURLConnection) urlConnection;
	
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			connection.setRequestMethod("GET");
			connection.setReadTimeout(timeout);
			
			// ObjectMapper uses UTF-8 by default
			// getInputStream() calls connect() itself to fire the HTTP request, 
			// no need to call connect() explicitly
			in = connection.getInputStream();
			int responseCode = connection.getResponseCode(); 
			if (responseCode == HttpURLConnection.HTTP_OK) { 
				// ObjectMapper uses UTF-8 by default
				String rawResponse = IOUtils.toString(in, "UTF-8");
				ObjectMapper mapper = new ObjectMapper();
				envelope = mapper.readValue(rawResponse, OtterEnvelope.class);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return envelope;
	}

}
