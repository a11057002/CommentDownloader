package ntou.cs.YTComment.entity;


public class Video {
	String videoId;
	String title;
	String description;
	String thumbnail;
	
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getVideoId() {
		return this.videoId;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String getThumbnail() {
		return this.thumbnail;
	}
	
}
