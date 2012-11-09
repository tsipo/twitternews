package ca.iskri.retweet.otter;

public class OtterEnvelope {
	private OtterRequest request;
	private OtterResponse response;

	public OtterRequest getRequest() {
		return request;
	}
	
	public void setRequest(OtterRequest request) {
		this.request = request;
	}
	
	public OtterResponse getResponse() {
		return response;
	}
	
	public void setResponse(OtterResponse response) {
		this.response = response;
	}
}
