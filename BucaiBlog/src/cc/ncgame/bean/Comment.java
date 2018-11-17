package cc.ncgame.bean;

public class Comment {
	private int id;
	private int bId;
	private String rName;
	private String name = "passer-by";
	private String email;
	private String content;
	private boolean is_email;
	private long commentTime;
	private String ip;

	public Comment(int id, int bId, String rName, String name, String email, String content, boolean is_email,
			long commentTime, String ip) {
		this.id = id;
		this.bId = bId;
		this.rName = rName;
		this.name = name;
		this.email = email;
		this.content = content;
		this.is_email = is_email;
		this.commentTime = commentTime;
		this.ip = ip;
	}

	public Comment(int bId, String rName, String name, String email, String content, boolean is_email,
			String ip) {
		this.bId = bId;
		this.rName = rName;
		this.name = name;
		this.email = email;
		this.content = content;
		this.is_email = is_email;
		this.ip = ip;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
	}

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isIs_email() {
		return is_email;
	}

	public void setIs_email(boolean is_email) {
		this.is_email = is_email;
	}

	public long getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(long commentTime) {
		this.commentTime = commentTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
