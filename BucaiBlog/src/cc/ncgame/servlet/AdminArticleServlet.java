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

import cc.ncgame.service.impl.ArticleServiceImpl;

/**
 * Servlet implementation class AdminArticleServlet
 */
@WebServlet("/AdminArticle")
public class AdminArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();

		HttpSession session = request.getSession();
		Object attribute = session.getAttribute("isAdmin");
		if(attribute!=null) {
			boolean parseBoolean = Boolean.parseBoolean(attribute.toString());
			if(parseBoolean) {
				

				ArticleServiceImpl articleServiceImpl = new ArticleServiceImpl();
				String queryLatelyArticle = articleServiceImpl.queryLatelyArticle();
				
				writer.println(queryLatelyArticle);
				
			}
		}else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 999).put("message", "无权限");
			writer.println(jsonObject.toString());
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
