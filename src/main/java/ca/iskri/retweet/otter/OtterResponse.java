package ca.iskri.retweet.otter;

import java.util.List;

public class OtterResponse {

	private Integer page;
	private Integer trackback_total;
	private Integer total;
	private Integer perpage;
	private Integer last_offset;
	private String topsy_trackback_url;
	private Integer hidden;
	private List<OtterStatus> list;
	private Integer offset;
	
	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getTrackback_total() {
		return trackback_total;
	}
	
	public void setTrackback_total(Integer trackback_total) {
		this.trackback_total = trackback_total;
	}
	
	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Integer getPerpage() {
		return perpage;
	}
	
	public void setPerpage(Integer perpage) {
		this.perpage = perpage;
	}
	
	public Integer getLast_offset() {
		return last_offset;
	}
	
	public void setLast_offset(Integer last_offset) {
		this.last_offset = last_offset;
	}
	
	public String getTopsy_trackback_url() {
		return topsy_trackback_url;
	}
	
	public void setTopsy_trackback_url(String topsy_trackback_url) {
		this.topsy_trackback_url = topsy_trackback_url;
	}
	
	public Integer getHidden() {
		return hidden;
	}
	
	public void setHidden(Integer hidden) {
		this.hidden = hidden;
	}
	
	public List<OtterStatus> getList() {
		return list;
	}
	
	public void setList(List<OtterStatus> list) {
		this.list = list;
	}
	
	public Integer getOffset() {
		return offset;
	}
	
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
}
