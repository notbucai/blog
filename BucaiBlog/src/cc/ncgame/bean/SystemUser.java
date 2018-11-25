package cc.ncgame.bean;

public class SystemUser {
	private int id;
	private String sname;
	private String spwd;
	private long login_time;
	private String login_ip;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSpwd() {
		return spwd;
	}
	public void setSpwd(String spwd) {
		this.spwd = spwd;
	}
	public long getLogin_time() {
		return login_time;
	}
	public void setLogin_time(long login_time) {
		this.login_time = login_time;
	}
	public String getLogin_ip() {
		return login_ip;
	}
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}
	public SystemUser(int id, String sname, String spwd, long login_time, String login_ip) {
		this.id = id;
		this.sname = sname;
		this.spwd = spwd;
		this.login_time = login_time;
		this.login_ip = login_ip;
	}
	public SystemUser(String sname, String spwd, String login_ip) {
		this.sname = sname;
		this.spwd = spwd;
		this.login_ip = login_ip;
	}
	
	
}
