package cc.ncgame.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import cc.ncgame.service.ICommentService;
import cc.ncgame.service.impl.CommentServiceImpl;
import cc.ncgame.util.ClientUtil;
import cc.ncgame.util.RexUtil;

/**
 * Servlet implementation class AddComment
 */
@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComment() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		BufferedReader in = request.getReader();
		HttpSession session = request.getSession();

		JSONObject errRes = new JSONObject();
		errRes.put("code", -1);
		errRes.put("message", "未知错误");
		String contentType = request.getContentType();
		System.out.println(contentType);
		if (contentType.indexOf("application/json") == -1) {
			errRes.put("code", 100);
			errRes.put("message", "请求类型错误");
			out.print(errRes.toString());
			return;
		}

		try {

			String json_x = in.readLine();
			JSONObject jsonObject = new JSONObject(json_x);

			String name = jsonObject.getString("name");

			if (name.length() > 10) {
				errRes.put("code", 101);
				errRes.put("message", "名字长度不能大于10");
				out.print(errRes.toString());
				return;
			}

			int bId = jsonObject.getInt("bId");

			int rId = jsonObject.getInt("rId");
			
			String email = null;
			boolean is_email = false;
			try {
				email = jsonObject.getString("email");
			} catch (Exception e) {

			}

			if (email != null && !"".equals(email.trim()) && !RexUtil.rexEmail(email)) {
				errRes.put("code", 102);
				errRes.put("message", "邮箱格式错误");
				out.print(errRes.toString());
				is_email = true;
				return;
			}

			String content = jsonObject.getString("content");

			if (content.length() > 500) {
				errRes.put("code", 103);
				errRes.put("message", "评论内容不得大于300个字符");
				out.print(errRes.toString());
				return;
			}
//			System.out.println(content);
			String ip = ClientUtil.getIpAddr(request);

			ICommentService commentService = (ICommentService) session.getAttribute("commentService");

			Object interval_attribute = session.getAttribute("interval");

			if (interval_attribute == null) {
				interval_attribute = 0;
				session.setAttribute("interval", new Date().getTime());
			}

			long interval = Long.parseLong(interval_attribute.toString());

			if (new Date().getTime() - interval <= 1000 * 60) {
				errRes.put("code", 106);
				errRes.put("message", "评论间隔需要大于1分钟");
				out.print(errRes.toString());
				return;
			} else {
				session.setAttribute("interval", new Date().getTime());
			}

			if (session.isNew() || commentService == null) {
				commentService = new CommentServiceImpl();
				session.setAttribute("commentService", commentService);
			}

			String result = commentService.addComment(bId, rId, name, email, content, is_email, ip);

			out.println(result);

		} catch (JSONException e) {
			errRes.put("code", 104);
			errRes.put("message", e.getMessage());
			out.print(errRes.toString());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
