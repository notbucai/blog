package cc.ncgame.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cc.ncgame.dao.ILinkDao;
import cc.ncgame.util.DBUtil;

public class LinkDaoImpl implements ILinkDao {

	@Override
	public List<Map<String, Object>> queryLinks() throws SQLException {
		String sql = "SELECT * FROM links  order by id desc";

		List<Map<String, Object>> executeAllQuery = new DBUtil().executeAllQuery(sql);

		return executeAllQuery;
	}

	@Override
	public List<Map<String, Object>> queryRandLinks() throws SQLException {
//		 SELECT * FROM links order by rand();
		String sql = "SELECT * FROM links order by rand()";

		List<Map<String, Object>> executeAllQuery = new DBUtil().executeAllQuery(sql);

		return executeAllQuery;
	}

}
