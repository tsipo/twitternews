package ca.iskri.retweet.otter;

public class OtterStatus {

	private String permalink_url;
	private Long date;
	private String content;
	private String date_alpha;
	private OtterAuthor author;
	private String type;
	private String highlight;

	public String getPermalink_url() {
		return permalink_url;
	}

	public void setPermalink_url(String permalink_url) {
		this.permalink_url = permalink_url;
	}

	public Long getDate() {
		return date;
	}
	
	public void setDate(Long date) {
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getDate_alpha() {
		return date_alpha;
	}
	
	public void setDate_alpha(String date_alpha) {
		this.date_alpha = date_alpha;
	}
	
	public OtterAuthor getAuthor() {
		return author;
	}
	
	public void setAuthor(OtterAuthor author) {
		this.author = author;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getHighlight() {
		return highlight;
	}
	
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}
	
}
