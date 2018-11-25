package cc.ncgame.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cc.ncgame.bean.Link;

public interface ILinkDao {
	List<Map<String,Object>> queryLinks() throws SQLException;
	int addLink(Link link) throws SQLException;
	int updateLink(Link link) throws SQLException;
	boolean deleteLink(int id) throws SQLException;
	List<Map<String,Object>> queryRandLinks()throws SQLException;
}
