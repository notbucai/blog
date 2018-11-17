package cc.ncgame.bean;

public class Article {

	private int id;
	private String title;
	private String content;
	private String info;
	private long timestamp;
	private long end_timestamp;
	private int hits;

	public Article(int id, String title, String content, String info, long timestamp, long end_timestamp, int hits) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.info = info;
		this.timestamp = timestamp;
		this.end_timestamp = end_timestamp;
		this.hits = hits;
	}

	public Article(int id, String title, String content, String info, long timestamp, int hits) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.info = info;
		this.timestamp = timestamp;
		this.hits = hits;
	}

	public Article(int id, String title, String content, String info, int hits) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.info = info;
		this.hits = hits;
	}

	public Article(String title, String content, String info) {
		this.title = title;
		this.content = content;
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getEnd_timestamp() {
		return end_timestamp;
	}

	public void setEnd_timestamp(long end_timestamp) {
		this.end_timestamp = end_timestamp;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

}
