package cc.ncgame.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import cc.ncgame.dao.impl.LinkDaoImpl;
import cc.ncgame.service.ILinkService;

public class LinkServiceImpl implements ILinkService {

	private JSONObject jsonObject = new JSONObject();
	private int code = -1;
	private String message = "错误";
	
	
	@Override
	public String getLinks() {
		
		try {
			
			LinkDaoImpl linkDaoImpl = new LinkDaoImpl();
			List<Map<String, Object>> queryLinks = linkDaoImpl.queryLinks();
			
			jsonObject.put("result", queryLinks);
			
		} catch (SQLException e) {
			e.printStackTrace();
			code = 101;
			message = "SQL错误";
		}catch (Exception e) {
			e.printStackTrace();
			code = 102;
			message = "错误";
		}
		
		jsonObject.put("code", code);
		jsonObject.put("message", message);
		
		return jsonObject.toString();
	}

	@Override
	public String getRandLinks() {

		try {
			
			LinkDaoImpl linkDaoImpl = new LinkDaoImpl();
			List<Map<String, Object>> queryLinks = linkDaoImpl.queryRandLinks();
			
			jsonObject.put("result", queryLinks);

			code = 0;
			message = "成功";
		} catch (SQLException e) {
			e.printStackTrace();
			code = 101;
			message = "SQL错误";
		}catch (Exception e) {
			e.printStackTrace();
			code = 102;
			message = "错误";
		}
		
		jsonObject.put("code", code);
		jsonObject.put("message", message);
		
		return jsonObject.toString();
	}

}
