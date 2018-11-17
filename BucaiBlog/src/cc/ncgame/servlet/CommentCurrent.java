package cc.ncgame.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import cc.ncgame.service.ICommentService;
import cc.ncgame.service.impl.CommentServiceImpl;

/**
 * Servlet implementation class CommentCurrent
 */
@WebServlet("/CommentCurrent")
public class CommentCurrent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentCurrent() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		String id_x = request.getParameter("id");

		try {

			int id = Integer.parseInt(id_x);

			ICommentService commentService = (ICommentService) session.getAttribute("commentService");

			if (session.isNew() || commentService == null) {
				commentService = new CommentServiceImpl();
				session.setAttribute("commentService", commentService);
			}

			String queryCurrentComments = commentService.queryCurrentComments(id);

			out.append(queryCurrentComments);

		} catch (NumberFormatException e) {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 2);
			jsonObject.put("message", "参数错误");
			out.append(jsonObject.toString());

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("服务器错误");
		}

	}

}
