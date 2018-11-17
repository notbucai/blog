package cc.ncgame.bean;

public class Link {
	private int id;
	private String title;
	private String info;
	private String link;
	private String imgPath;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImg() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public Link(int id, String title, String info, String link, String imgPath) {
		super();
		this.id = id;
		this.title = title;
		this.info = info;
		this.link = link;
		this.imgPath = imgPath;
	}
	public Link(String title, String info, String link, String imgPath) {
		super();
		this.title = title;
		this.info = info;
		this.link = link;
		this.imgPath = imgPath;
	}
	public Link() {
		super();
	}
	
}
