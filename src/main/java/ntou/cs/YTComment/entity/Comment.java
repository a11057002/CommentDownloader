package ntou.cs.YTComment.entity;

import org.springframework.stereotype.Component;

@Component
public class Comment {

	private String name;
	private String text;
	private String img;
	private String time;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setText(String text) {
		this.text= text;
	}
	
	public void setImg(String img) {
		this.img=img;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getImg() {
		return this.img;
	}
	
	public String getTime() {
		return this.time;
	}
	
	@Override
	public String toString(){
		return "{\"time\":\""+time+"\",\"img\":\""+img+"\",\"name\":\""+name+"\",\"text\":\""+text+"\"}";
	}
	
	
}
