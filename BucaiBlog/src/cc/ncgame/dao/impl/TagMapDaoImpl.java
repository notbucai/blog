package cc.ncgame.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cc.ncgame.bean.TagMap;
import cc.ncgame.dao.ITagMapDao;
import cc.ncgame.util.DBUtil;

public class TagMapDaoImpl implements ITagMapDao {

	@Override
	public int insertTagMap(TagMap tagMap) throws SQLException {

		String sql = "INSERT INTO tagMap (tID,aID) VALUES (?,?)";

		int executeUpdateGetID = new DBUtil().executeUpdateGetID(sql, tagMap.gettID(), tagMap.getaID());

		return executeUpdateGetID;
	}

	@Override
	public List<Map<String, Object>> getTagIds(int aID) throws SQLException {

		String sql = "select tID from tagMap where aID=?";

		List<Map<String, Object>> executeAllQuery = new DBUtil().executeAllQuery(sql, aID);

		return executeAllQuery;
	}
	@Override
	public List<Map<String, Object>> getArticleIds(int tID) throws SQLException {

		String sql = "select aID from tagMap where tID=?";

		List<Map<String, Object>> executeAllQuery = new DBUtil().executeAllQuery(sql, tID);

		return executeAllQuery;
	}
//	select tID,count(0) as count from tagMap group by tID;

	@Override
	public List<Map<String, Object>> countTagIds() throws SQLException {

		String sql = "select tID,count(0) as count from tagMap group by tID";

		List<Map<String, Object>> executeAllQuery = new DBUtil().executeAllQuery(sql);

		return executeAllQuery;
	}

}
