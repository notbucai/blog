package cc.ncgame.dao;

import java.sql.SQLException;

import cc.ncgame.bean.Tag;

public interface ITagDao {
	 int getTagId(String tagName) throws SQLException;
	
	int insertTag(Tag tag) throws SQLException;
	
	String queryTag(int id) throws SQLException;
	
	int queryTagId(String tagName) throws SQLException;
	
}
