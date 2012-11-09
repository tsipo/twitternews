package ca.iskri.retweet.otter;

public class OtterRequest {
	
	private OtterRequestParameters parameters;
	private String response_type;
	private String resource;
	private String url;
	
	public OtterRequestParameters getParameters() {
		return parameters;
	}
	
	public void setParameters(OtterRequestParameters parameters) {
		this.parameters = parameters;
	}
	
	public String getResponse_type() {
		return response_type;
	}
	
	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}
	
	public String getResource() {
		return resource;
	}
	
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
