package cc.ncgame.bean;

public class Tag {
	private int id;
	private String tag_name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	public Tag(int id, String tag_name) {
		super();
		this.id = id;
		this.tag_name = tag_name;
	}
	public Tag(String tag_name) {
		super();
		this.tag_name = tag_name;
	}
	
}
