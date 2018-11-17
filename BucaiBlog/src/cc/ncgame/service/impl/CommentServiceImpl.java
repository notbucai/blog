package cc.ncgame.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import cc.ncgame.bean.Comment;
import cc.ncgame.dao.ICommentDao;
import cc.ncgame.dao.impl.CommentDaoImpl;
import cc.ncgame.service.ICommentService;

public class CommentServiceImpl implements ICommentService {

	@Override
	public String queryCurrentComments(int bId) {

		ICommentDao commentDao = new CommentDaoImpl();
		JSONObject jsonObject = new JSONObject();
		int code = -1;
		String message = "Unknown Error";

		try {

			List<Map<String, Object>> queryCurrentArticle = commentDao.queryCurrentComment(bId);

			if (queryCurrentArticle == null) {
				code = 1;
				message = "查询失败";
			} else if (queryCurrentArticle.size() <= 0) {
				code = 0;
				message = "木有数据";
			} else {
				code = 0;
				message = "成功";
				jsonObject.put("result", queryCurrentArticle);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		jsonObject.put("code", code);
		jsonObject.put("message", message);

		System.out.println(jsonObject.toString());

		return jsonObject.toString();

	}

	public String addComment(int bId, int rId, String name, String email, String content, boolean is_email,
			String ip) {

		ICommentDao commentDao = new CommentDaoImpl();
		JSONObject jsonObject = new JSONObject();
		int code = -1;
		
		String message = "Unknown Error";

		try {
			String rName = commentDao.queryIdToCommentName(rId);
			
			Comment comment = new Comment(bId, rName, name, email, content, is_email, ip);
			
			boolean addComment = commentDao.addComment(comment);

			if (!addComment) {
				code = 1;
				message = "失败";
			} else {
				code = 0;
				message = "成功";
				jsonObject.put("result", addComment);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		jsonObject.put("code", code);
		jsonObject.put("message", message);

		System.out.println(jsonObject.toString());

		return jsonObject.toString();

	}
}
