package cc.ncgame.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import cc.ncgame.bean.Link;
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
			code = 0;
			message = "成功";
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

	@Override
	public String addLink(String title, String imgPath, String info, String link_url) {

		try {

			LinkDaoImpl linkDaoImpl = new LinkDaoImpl();
			Link link = new Link();
			link.setTitle(title);
			link.setImgPath(imgPath);
			link.setInfo(info);
			link.setLink(link_url);

			int addLink = linkDaoImpl.addLink(link);

			if (addLink > 0) {
				code = 0;
				message = "成功";
			} else {
				code = 104;
				message = "失败";
			}

//			jsonObject.put("result", );

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
		} catch (Exception e) {
			e.printStackTrace();
			code = 102;
			message = "错误";
		}

		jsonObject.put("code", code);
		jsonObject.put("message", message);

		return jsonObject.toString();
	}

	
	@Override
	public String updateLink(int id, String title, String imgPath, String info, String link_url) {
		try {

			LinkDaoImpl linkDaoImpl = new LinkDaoImpl();
			Link link = new Link();
			link.setId(id);
			link.setTitle(title);
			link.setImgPath(imgPath);
			link.setInfo(info);
			link.setLink(link_url);

			int addLink = linkDaoImpl.updateLink(link);

			if (addLink > 0) {
				code = 0;
				message = "成功";
			} else {
				code = 104;
				message = "失败";
			}

//			jsonObject.put("result", );

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

	@Override
	public String deleteLink(int id) {
		try {

			LinkDaoImpl linkDaoImpl = new LinkDaoImpl();


			boolean re = linkDaoImpl.deleteLink(id);

			if (re) {
				code = 0;
				message = "成功";
			} else {
				code = 104;
				message = "失败";
			}

//			jsonObject.put("result", );

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
