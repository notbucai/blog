package cc.ncgame.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cc.ncgame.bean.Link;
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

	@Override
	public int addLink(Link link) throws SQLException {
		String sql = "INSERT INTO links (title,info,link,img_path) VALUES (?,?,?,?)";

		String title = link.getTitle();
		String link_url = link.getLink();
		String info = link.getInfo();
		String img_path = link.getImg();
		DBUtil dbUtil = new DBUtil();
		int executeUpdate = dbUtil.executeUpdateGetID(sql, title, info, link_url, img_path);

		System.out.println(executeUpdate);

		return executeUpdate;

	}

	@Override
	public int updateLink(Link link) throws SQLException {
		// update systemUser set login_ip='localhost:8888' where id=1;

		String sql = "update links set title=?,info=?,link=?,img_path=? where id=?";

		String title = link.getTitle();
		int id = link.getId();
		String link_url = link.getLink();
		String info = link.getInfo();
		String img_path = link.getImg();
		DBUtil dbUtil = new DBUtil();

		int executeUpdate = dbUtil.executeUpdate(sql, title, info, link_url, img_path, id);

		return executeUpdate;

	}

	@Override
	public boolean deleteLink(int id) throws SQLException {
		String sql = "delete from links where id=?";
		DBUtil dbUtil = new DBUtil();
		int executeUpdate = dbUtil.executeUpdate(sql, id);
		System.out.println(executeUpdate);
		if (executeUpdate > 0) {
			return true;
		}

		return false;
	}

}
