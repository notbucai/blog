package cc.ncgame.dao;

import java.sql.SQLException;

import cc.ncgame.bean.SystemUser;

public interface ISystemUserDao {
	boolean login(SystemUser systemUser) throws SQLException;
	boolean updateLoginIp(String ip) throws SQLException;
}
