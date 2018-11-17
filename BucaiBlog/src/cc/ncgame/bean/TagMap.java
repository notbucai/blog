package cc.ncgame.bean;

public class TagMap {
	private int id;
	private int aID;
	private int tID;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getaID() {
		return aID;
	}
	public void setaID(int aID) {
		this.aID = aID;
	}
	public int gettID() {
		return tID;
	}
	public void settID(int tID) {
		this.tID = tID;
	}
	public TagMap(int id, int aID, int tID) {
		super();
		this.id = id;
		this.aID = aID;
		this.tID = tID;
	}
	public TagMap(int aID, int tID) {
		this.aID = aID;
		this.tID = tID;
	}
	
	
}
