package cc.ncgame.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cc.ncgame.bean.Tag;
import cc.ncgame.dao.ITagDao;
import cc.ncgame.util.DBUtil;

public class TagDaoImpl implements ITagDao {

	@Override
	public int getTagId(String tagName) throws SQLException {

		String sql = "select id from tags where tag_name=?";

		List<Map<String, Object>> executeAllQuery = new DBUtil().executeAllQuery(sql, tagName);

		if (executeAllQuery.size() > 0) {

			Map<String, Object> map = executeAllQuery.get(0);

			int id = Integer.parseInt(map.get("id").toString());
			return id;
		}

		return 0;
	}

	@Override
	public int insertTag(Tag tag) throws SQLException {

		String sql = "INSERT INTO tags (tag_name) VALUES (?)";

		String tag_name = tag.getTag_name();

		int executeUpdate = new DBUtil().executeUpdateGetID(sql, tag_name);

		return executeUpdate;
	}

	@Override
	public int queryTagId(String tagName) throws SQLException {

		
		String sql = "select id as count from tags where tag_name=?";

		List<Map<String, Object>> executeAllQuery = new DBUtil().executeAllQuery(sql, tagName);

		if (executeAllQuery.size() > 0) {

			Map<String, Object> map = executeAllQuery.get(0);

			int count = Integer.parseInt(map.get("id").toString());
			
			return count;

		}
		
		return 0;
	}

	@Override
	public String queryTag(int id) throws SQLException {

		String sql = "select tag_name from tags where id=?";

		List<Map<String, Object>> executeAllQuery = new DBUtil().executeAllQuery(sql, id);

		if (executeAllQuery.size() > 0) {

			Map<String, Object> map = executeAllQuery.get(0);

			Object object = map.get("tag_name");
			
			return object.toString();

		}
		
		return null;
	}

}
