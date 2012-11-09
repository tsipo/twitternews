package ca.iskri.retweet.otter;

public class OtterRequestParameters {
	
	private String orig_url;
	private String redirected_url;
	private Integer page;
	private Integer perpage;
	private String sort_method;
	private String tracktype;
	private String url;
	private String mintime;
	private String maxtime;
	
	
	public String getOrig_url() {
		return orig_url;
	}
	
	public void setOrig_url(String orig_url) {
		this.orig_url = orig_url;
	}
	
	public String getRedirected_url() {
		return redirected_url;
	}
	
	public void setRedirected_url(String redirected_url) {
		this.redirected_url = redirected_url;
	}
	
	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getPerpage() {
		return perpage;
	}
	
	public void setPerpage(Integer perpage) {
		this.perpage = perpage;
	}
	
	public String getSort_method() {
		return sort_method;
	}
	
	public void setSort_method(String sort_method) {
		this.sort_method = sort_method;
	}
	
	public String getTracktype() {
		return tracktype;
	}
	
	public void setTracktype(String tracktype) {
		this.tracktype = tracktype;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMintime() {
		return mintime;
	}

	public void setMintime(String mintime) {
		this.mintime = mintime;
	}

	public String getMaxtime() {
		return maxtime;
	}

	public void setMaxtime(String maxtime) {
		this.maxtime = maxtime;
	}
	
	
}
