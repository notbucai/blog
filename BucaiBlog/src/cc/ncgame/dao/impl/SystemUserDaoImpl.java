package cc.ncgame.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cc.ncgame.bean.SystemUser;
import cc.ncgame.dao.ISystemUserDao;
import cc.ncgame.util.DBUtil;

public class SystemUserDaoImpl implements ISystemUserDao {

	@Override
	public boolean login(SystemUser systemUser) throws SQLException {

		String sql = "select count(0) as count from systemUser where sname=? and spwd=?";
		DBUtil dbUtil = new DBUtil();

		List<Map<String, Object>> executeAllQuery = dbUtil.executeAllQuery(sql, systemUser.getSname(),
				systemUser.getSpwd());

		if (executeAllQuery.size() > 0) {
			if (Integer.parseInt(executeAllQuery.get(0).get("count").toString()) > 0) {
				sql = "update systemUser set login_ip=?";
				System.out.println(executeAllQuery);
				dbUtil.executeUpdate(sql, systemUser.getLogin_ip());
				return true;
			}

		}

		return false;
	}

	@Override
	public boolean updateLoginIp(String ip) throws SQLException {

		return false;
	}

}
