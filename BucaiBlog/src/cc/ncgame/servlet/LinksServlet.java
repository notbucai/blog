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

import cc.ncgame.service.impl.LinkServiceImpl;

/**
 * Servlet implementation class Links
 */
@WebServlet("/Links")
public class LinksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LinksServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject errJson = new JSONObject();
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		Object attribute = session.getAttribute("isAdmin");
		if (attribute != null) {
			boolean parseBoolean = Boolean.parseBoolean(attribute.toString());
			if (parseBoolean) {

				try {

					LinkServiceImpl linkService = new LinkServiceImpl();
					String randLinks = linkService.getLinks();
					out.println(randLinks);

				} catch (Exception e) {

					errJson.put("code", 104);
					errJson.put("message", "错误");
					out.print(errJson.toString());
				}

			}
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 999).put("message", "无权限");
			out.println(jsonObject.toString());
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
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
		HttpSession session = req.getSession();
		Object attribute = session.getAttribute("isAdmin");
		if (attribute != null) {
			boolean parseBoolean = Boolean.parseBoolean(attribute.toString());
			if (parseBoolean) {

				String readLine = reader.readLine();

				try {
					JSONObject jsonObject = new JSONObject(readLine);
					String title = jsonObject.getString("title");
					String info = jsonObject.getString("info");
					String imgPath = jsonObject.getString("img_path");
					String link_url = jsonObject.getString("link");
					if (title == null || "".equals(title.trim()) || imgPath == null || "".equals(imgPath.trim())) {
						errJson.put("code", 105);
						errJson.put("message", "不能为空");
						out.append(errJson.toString());

						return;
					}

					LinkServiceImpl linkServiceImpl = new LinkServiceImpl();
					String addLink = linkServiceImpl.addLink(title, imgPath, info, link_url);

					out.append(addLink);

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
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 999).put("message", "无权限");
			out.println(jsonObject.toString());
		}

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

		HttpSession session = req.getSession();
		Object attribute = session.getAttribute("isAdmin");
		if (attribute != null) {
			boolean parseBoolean = Boolean.parseBoolean(attribute.toString());
			if (parseBoolean) {

				String readLine = reader.readLine();

				try {
					JSONObject jsonObject = new JSONObject(readLine);
					int id = jsonObject.getInt("id");
					String title = jsonObject.getString("title");
					String info = jsonObject.getString("info");
					String imgPath = jsonObject.getString("img_path");
					String link_url = jsonObject.getString("link");
					if (title == null || "".equals(title.trim()) || imgPath == null || "".equals(imgPath.trim())) {
						errJson.put("code", 105);
						errJson.put("message", "不能为空");
						out.append(errJson.toString());

						return;
					}

					LinkServiceImpl linkServiceImpl = new LinkServiceImpl();
					String addLink = linkServiceImpl.updateLink(id, title, imgPath, info, link_url);

					out.append(addLink);

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
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 999).put("message", "无权限");
			out.println(jsonObject.toString());
		}

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();

		HttpSession session = req.getSession();
		Object attribute = session.getAttribute("isAdmin");
		if (attribute != null) {
			boolean parseBoolean = Boolean.parseBoolean(attribute.toString());
			if (parseBoolean) {

				String id_x = req.getParameter("id");
				try {

					int id = Integer.parseInt(id_x);

					LinkServiceImpl linkServiceImpl = new LinkServiceImpl();
					String deleteLink = linkServiceImpl.deleteLink(id);

					out.print(deleteLink);

				} catch (NumberFormatException e) {

					JSONObject jsonObject = new JSONObject();
					jsonObject.put("code", 2);
					jsonObject.put("message", "参数错误");
					out.append(jsonObject.toString());

				} catch (Exception e) {
					System.err.println("服务器错误");
				}

			}
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 999).put("message", "无权限");
			out.println(jsonObject.toString());
		}

	}

}
