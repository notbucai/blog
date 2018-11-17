package cc.ncgame.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cc.ncgame.service.impl.LinkServiceImpl;

/**
 * Servlet implementation class LinksRand
 */
@WebServlet("/LinksRand")
public class LinksRandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LinksRandServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		JSONObject errJson = new JSONObject();
		PrintWriter out = response.getWriter();
		
		try {
			
			LinkServiceImpl linkService = new LinkServiceImpl();
			
			String randLinks = linkService.getRandLinks();
			
			out.println(randLinks);
			
		} catch (Exception e) {
			
			errJson.put("code", 111);
			errJson.put("message", "错误");
			out.print(errJson.toString());
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
