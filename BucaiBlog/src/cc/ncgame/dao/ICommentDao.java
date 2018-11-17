package cc.ncgame.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cc.ncgame.bean.Comment;

public interface ICommentDao {
	
	public boolean addComment(Comment comment) throws SQLException;
	public boolean deleteComment(int id) throws SQLException;
	public boolean updateComment(Comment comment) throws SQLException;
	public List<Map<String, Object>> queryAllComment() throws SQLException;
	public List<Map<String, Object>> queryCurrentComment(int bId) throws SQLException;
	public String queryIdToCommentName(int id) throws SQLException;
	public boolean replyCurrentComment(Comment comment) throws SQLException;
	
	
}
