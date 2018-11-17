package cc.ncgame.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.ncgame.bean.Comment;
import cc.ncgame.dao.ICommentDao;
import cc.ncgame.util.DBUtil;

public class CommentDaoImpl implements ICommentDao {

	@Override
	public boolean addComment(Comment comment) throws SQLException {

		String sql = "insert into comment(bId,rName,name,email,is_email,content,ip) values (?,?,?,?,?,?,?);";

		int bId = comment.getbId();
		String name = comment.getName();
		String content = comment.getContent();
		String rName = comment.getrName();
		String email = comment.getEmail();
		boolean is_email = comment.isIs_email();
//		commentTime
		String ip = comment.getIp();
		DBUtil dbUtil = new DBUtil();
		int executeUpdate = dbUtil.executeUpdate(sql, bId, rName, name, email, is_email, content, ip);
//		System.out.println(executeUpdate);
		if (executeUpdate > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean deleteComment(int id) throws SQLException {
		String sql = "delete from comment where id=?";
		DBUtil dbUtil = new DBUtil();
		int executeUpdate = dbUtil.executeUpdate(sql, id);
		System.out.println(executeUpdate);
		if (executeUpdate > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateComment(Comment comment) throws SQLException {
		int id = comment.getId();

		int bId = comment.getbId();
		String rName = comment.getrName();
		String name = comment.getName();
		String content = comment.getContent();
		String email = comment.getEmail();
		boolean is_email = comment.isIs_email();
//		commentTime
		long commentTime = comment.getCommentTime();
		String ip = comment.getIp();

		int executeUpdate = -1;

		String sql;
		System.out.println((new Date().getTime()));
		Date date = new Date(commentTime);
		System.out.println(date);
		DBUtil dbUtil = new DBUtil();
		if (commentTime <= 0) {
			sql = "update comment set bId=?,rName=?,name=?,content=?,email=?,is_email=?,ip=? where id=?";

			executeUpdate = dbUtil.executeUpdate(sql, bId, rName, name, content, email, is_email, ip, id);
		} else {
			sql = "update comment set bId=?,rName=?,name=?,content=?,email=?,is_email=?,commentTime=?,ip=? where id=?";
			executeUpdate = dbUtil.executeUpdate(sql, bId, rName, name, content, email, is_email, commentTime, ip, id);
		}

		System.out.println(executeUpdate);
		if (executeUpdate > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> queryAllComment() throws SQLException {
		String sql = "select id,bId,rName,name,email,is_email,content,commentTime,login_ip from comment";

		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.executeAllQuery(sql);

//		System.out.println(list);

		return list;
	}

	@Override
	public List<Map<String, Object>> queryCurrentComment(int bId) throws SQLException {
		String sql = "select id,rName,name,content,commentTime from comment where bId=? ORDER BY commentTime DESC";

		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.executeAllQuery(sql, bId);

//		System.out.println(list);

		return list;
	}

	@Override
	public String queryIdToCommentName(int id) throws SQLException {

		String sql = "select name from comment where id=?";

		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.executeAllQuery(sql, id);
		if (list.size() > 0) {
			return list.get(0).get("name").toString();
		}

		return null;
	}

	@Override
	public boolean replyCurrentComment(Comment comment) throws SQLException {
		return false;
	}

}
