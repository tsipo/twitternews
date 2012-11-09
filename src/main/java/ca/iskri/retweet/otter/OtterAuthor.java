package ca.iskri.retweet.otter;

public class OtterAuthor {
	
	private String name;
	private String url;
	private String nick;
	private String topsy_author_url;
	private String photo_url;
	private Integer influence_level;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getTopsy_author_url() {
		return topsy_author_url;
	}
	
	public void setTopsy_author_url(String topsy_author_url) {
		this.topsy_author_url = topsy_author_url;
	}
	
	public String getPhoto_url() {
		return photo_url;
	}
	
	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	public Integer getInfluence_level() {
		return influence_level;
	}

	public void setInfluence_level(Integer influence_level) {
		this.influence_level = influence_level;
	}
	
}
