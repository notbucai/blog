package cc.ncgame.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.ncgame.bean.Article;
import cc.ncgame.dao.IArticleDao;
import cc.ncgame.util.DBUtil;

public class ArticleDaoImpl implements IArticleDao {

	public int addArticle(Article article) throws SQLException {

		String sql = "insert into article(title,content,info) values (?,?,?)";

		String title = article.getTitle();
		String content = article.getContent();
		String info = article.getInfo();
		DBUtil dbUtil = new DBUtil();
		int executeUpdate = dbUtil.executeUpdateGetID(sql, title, content, info);
		
		System.out.println(executeUpdate);

		return executeUpdate;
		
	}

	public boolean deleteArticle(int id) throws SQLException {

		String sql = "delete from article where id=?";
		DBUtil dbUtil = new DBUtil();
		int executeUpdate = dbUtil.executeUpdate(sql, id);
		System.out.println(executeUpdate);
		if (executeUpdate > 0) {
			return true;
		}

		return false;
	}

	public List<Map<String, Object>> queryAllArticle() throws SQLException {

		String sql = "select id,title,timestamp,end_timestamp,hits from article";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.executeAllQuery(sql);

//		System.out.println(list);

		return list;
	}
	
	public List<Map<String, Object>> queryLatelyArticle() throws SQLException {

		String sql = "select id,title,timestamp,end_timestamp,hits from article order by timestamp desc limit 0,20";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.executeAllQuery(sql);

//		System.out.println(list);

		return list;
	}

	public boolean updateArticle(Article article) throws SQLException {

		int id = article.getId();
		String title = article.getTitle();
		String content = article.getContent();
		String info = article.getInfo();
		long timestamp = article.getTimestamp();
		int hits = article.getHits();

		int executeUpdate = -1;

		String sql;
		System.out.println((new Date().getTime()));
		Date date = new Date(timestamp);
		System.out.println(date);
		DBUtil dbUtil = new DBUtil();
		if (timestamp <= 0) {
			sql = "update article set title=?,content=?,info=? where id=?";

			executeUpdate = dbUtil.executeUpdate(sql, title, content, info, id);
		} else {
			sql = "update article set title=?,content=?,info=?,timestamp=?,hits=? where id=?";

			executeUpdate = dbUtil.executeUpdate(sql, title, content, info, new Date(timestamp), hits, id);
		}

		System.out.println(executeUpdate);
		if (executeUpdate > 0) {
			return true;
		}
		return false;
	}

	public List<Map<String, Object>> queryArticlePage(int limit, int offset) throws SQLException {

		String sql = "select id,title,info,timestamp,hits from article order by end_timestamp desc limit ?,?";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.executeAllQuery(sql, offset, limit);

		System.out.println(list);

		return list;
//		select * from orders_history where type=8 limit 1000,10;

	}

	public int queryArticleCount() throws SQLException {

		String sql = "select count(1) as count from article";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> executeAllQuery = dbUtil.executeAllQuery(sql);
		int count = 0;
		if (executeAllQuery.size() > 0) {

			count = Integer.valueOf(executeAllQuery.get(0).get("count").toString());

		}

		return count;

	}

	public Map<String, Object> queryOneArticle(int id) throws SQLException {

		String sql = "select id,title,info,timestamp,content,hits from article where id=?";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.executeAllQuery(sql, id);

		if (list.size() >= 1)
			return list.get(0);
		else
			return null;
	}

	public List<Map<String, Object>> queryActiclesYear() throws SQLException {

//		select count(0) as count, FROM_UNIXTIME(UNIX_TIMESTAMP(timestamp),"%Y") as year from article group by FROM_UNIXTIME(UNIX_TIMESTAMP(timestamp),"%Y");

		String sql = "select count(0) as count, FROM_UNIXTIME(UNIX_TIMESTAMP(timestamp),\"%Y\") as year from article group by FROM_UNIXTIME(UNIX_TIMESTAMP(timestamp),\"%Y\")  order by year desc;";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> executeAllQuery = dbUtil.executeAllQuery(sql);

		return executeAllQuery;
	}

	public List<Map<String, Object>> queryYearToPigeonhole(int year) throws SQLException {
//		select id,title,FROM_UNIXTIME(UNIX_TIMESTAMP(timestamp),"%m-%d") from article where  FROM_UNIXTIME(UNIX_TIMESTAMP(timestamp),"%Y")=2018 order by timestamp desc;
		String sql = "select id,title,FROM_UNIXTIME(UNIX_TIMESTAMP(timestamp),\"%m-%d\") as timestamp from article where  FROM_UNIXTIME(UNIX_TIMESTAMP(timestamp),\"%Y\")=? order by timestamp desc;";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> executeAllQuery = dbUtil.executeAllQuery(sql, year);

		return executeAllQuery;
	}

	@Override
	public boolean updateActicleHits(int id) throws SQLException {

//		UPDATE article SET hits =hits+1  WHERE id = 1

		String sql = "UPDATE article SET hits = hits+1  WHERE id = ?";
		DBUtil dbUtil = new DBUtil();
		int executeUpdate = dbUtil.executeUpdate(sql, id);

		if (executeUpdate > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> queryOneArticleInfo(int id) throws SQLException {
		String sql = "select  id,title,FROM_UNIXTIME(UNIX_TIMESTAMP(timestamp),\"%Y-%m-%d\") as timestamp from article where id=?";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.executeAllQuery(sql, id);

		if (list.size() >= 1)
			return list.get(0);
		else
			return null;
	}
}
