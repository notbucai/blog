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

import cc.ncgame.service.IArticleService;
import cc.ncgame.service.impl.ArticleServiceImpl;

/**
 * Servlet implementation class ArticleServlet
 */
@WebServlet("/ArticlePage")
public class ArticlePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ArticlePageServlet() {
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
		
		String limit_x = request.getParameter("limit");
		String offset_x = request.getParameter("offset");

		try {
			
			int limit = Integer.parseInt(limit_x);
			int offset = Integer.parseInt(offset_x);
			
			IArticleService articleService = (IArticleService) session.getAttribute("articleService");

			if (session.isNew() || articleService == null) {
				articleService = new ArticleServiceImpl();
				session.setAttribute("articleService", articleService);
			}

			String queryArticlePage = articleService.queryArticlePage(limit, offset);

			out.append(queryArticlePage);
			
		}catch(NumberFormatException e) {
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 2);
			jsonObject.put("message", "参数错误");
			out.append(jsonObject.toString());
			
		}catch (Exception e) {
			System.err.println("服务器错误");
		}

	
	}


}
