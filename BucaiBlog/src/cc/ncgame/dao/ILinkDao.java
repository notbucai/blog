package cc.ncgame.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ILinkDao {
	List<Map<String,Object>> queryLinks() throws SQLException;
	List<Map<String,Object>> queryRandLinks()throws SQLException;
}
