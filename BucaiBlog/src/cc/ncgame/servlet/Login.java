package cc.ncgame.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import cc.ncgame.service.impl.LoginServiceImpl;
import cc.ncgame.util.ClientUtil;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String contentType = req.getContentType();

		PrintWriter out = resp.getWriter();

		BufferedReader reader = req.getReader();

		JSONObject errJson = new JSONObject();
		System.out.println(contentType);
		if (contentType.indexOf("application/json") == -1) {
			errJson.put("code", 100);
			errJson.put("message", "type error");
			out.append(errJson.toString());
			return;
		}

		String readLine = reader.readLine();

		try {
			JSONObject jsonObject = new JSONObject(readLine);
			String sname = jsonObject.getString("sname");
			String spwd = jsonObject.getString("spwd");
			if (sname == null || "".equals(sname.trim()) || spwd == null || "".equals(spwd.trim())) {
				errJson.put("code", 105);
				errJson.put("message", "不能为空");
				
				
				out.append(errJson.toString());

				return;
			}
			String ip = ClientUtil.getIpAddr(req);
			
			LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
			String login = loginServiceImpl.login(sname, spwd, ip);
			JSONObject jsonObject2 = new JSONObject(login);
			int code = jsonObject2.getInt("code");
			if(code == 0) {
				
				HttpSession session = req.getSession();
				session.setAttribute("isAdmin", true);
				
			}
			out.append(login);

		} catch (JSONException e) {

			errJson.put("code", 102);
			errJson.put("message", "arges error");
			out.append(errJson.toString());

			return;

		} catch (Exception e) {

			errJson.put("code", 103);
			errJson.put("message", "error");
			out.append(errJson.toString());

			return;

		}

	
	}

}
