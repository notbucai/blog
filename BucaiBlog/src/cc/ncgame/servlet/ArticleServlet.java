package cc.ncgame.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cc.ncgame.service.IArticleService;
import cc.ncgame.service.impl.ArticleServiceImpl;

/**
 * Servlet implementation class ArticleServlet
 */
@WebServlet("/Article")
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ArticleServlet() {
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

			IArticleService articleService = (IArticleService) session.getAttribute("articleService");

			if (session.isNew() || articleService == null) {
				articleService = new ArticleServiceImpl();
				session.setAttribute("articleService", articleService);
			}

			String queryOneArticle = articleService.queryOneArticle(id);

			out.append(queryOneArticle);

		} catch (NumberFormatException e) {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 2);
			jsonObject.put("message", "参数错误");
			out.append(jsonObject.toString());

		} catch (Exception e) {
			System.err.println("服务器错误");
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String contentType = req.getContentType();

		PrintWriter out = resp.getWriter();

		BufferedReader reader = req.getReader();

		JSONObject errJson = new JSONObject();
		System.out.println(contentType);
		if (contentType.indexOf("application/json") ==-1){
			errJson.put("code", 100);
			errJson.put("message", "type error");
			out.append(errJson.toString());
			return;
		}

		String readLine = reader.readLine();

		try {
			JSONObject jsonObject = new JSONObject(readLine);
			String title = jsonObject.getString("title");
			String info = jsonObject.getString("info");
			String content = jsonObject.getString("content");
			JSONArray jsonArray = jsonObject.getJSONArray("tags");
			List<Object> list = jsonArray.toList();
			String[] tags = list.toArray(new String[0]);
			
			ArticleServiceImpl articleServiceImpl = new ArticleServiceImpl();
			String addArticle = articleServiceImpl.addArticle(title, info, content, tags);
			
			out.append(addArticle);
			
		} catch (JSONException e) {
			
			errJson.put("code", 102);
			errJson.put("message", "arges error");
			out.append(errJson.toString());
			
			return;
			
		}catch (Exception e) {
			
			errJson.put("code", 103);
			errJson.put("message", "error");
			out.append(errJson.toString());
			
			return;
			
		}

		
		
	}
}
