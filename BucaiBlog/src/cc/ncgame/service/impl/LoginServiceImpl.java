package cc.ncgame.service.impl;

import java.sql.SQLException;

import org.json.JSONObject;

import cc.ncgame.bean.SystemUser;
import cc.ncgame.dao.impl.SystemUserDaoImpl;
import cc.ncgame.service.ILoginService;
import cc.ncgame.util.EncryptionUtil;

public class LoginServiceImpl implements ILoginService {
	private JSONObject jsonObject = new JSONObject();
	private int code = -1;
	private String message = "错误";

	@Override
	public String login(String sname, String spwd, String login_ip) {

		try {
			
			String md5 = EncryptionUtil.MD5(spwd);
			
			SystemUser systemUser = new SystemUser(sname, md5, login_ip);
			SystemUserDaoImpl systemUserDaoImpl = new SystemUserDaoImpl();
			boolean login = systemUserDaoImpl.login(systemUser);
			if (login) {

				code = 0;
				message = "成功";
			} else {

				code = 105;
				message = "失败";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			code = 101;
			message = "SQL错误";
		} catch (Exception e) {
			e.printStackTrace();
			code = 102;
			message = "错误";
		}

		jsonObject.put("code", code);
		jsonObject.put("message", message);

		return jsonObject.toString();
	}

}
