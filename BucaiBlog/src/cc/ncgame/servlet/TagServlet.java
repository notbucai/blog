package cc.ncgame.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cc.ncgame.service.impl.TagServiceImpl;

/**
 * Servlet implementation class Tag
 */
@WebServlet("/Tag")
public class TagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TagServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tID_p = request.getParameter("id");
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = response.getWriter();
		try {
			
			int tID = Integer.parseInt(tID_p );
			
			TagServiceImpl tagServiceImpl = new TagServiceImpl();
			String currtentArticles = tagServiceImpl.getCurrtentArticles(tID);
			
			out.print(currtentArticles);
			
		}catch (NumberFormatException e) {
			e.printStackTrace();
			jsonObject.put("code", 101);
			jsonObject.put("message", "参数错误");
			out.println(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			jsonObject.put("code", 102);
			jsonObject.put("message", "错误");
			out.println(jsonObject.toString());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
