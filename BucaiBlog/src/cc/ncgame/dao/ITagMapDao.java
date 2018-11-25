package cc.ncgame.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cc.ncgame.bean.TagMap;

public interface ITagMapDao {

	int insertTagMap(TagMap tagMap) throws SQLException;
	
	List<Map<String, Object>> getTagIds(int aID) throws SQLException;

	List<Map<String, Object>> countTagIds() throws SQLException;
	List<Map<String, Object>> getArticleIds(int tID) throws SQLException;

	int deleteTagMaps(int id) throws SQLException;
}
