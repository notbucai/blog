package cc.ncgame.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter("/AdminFilter")
public class AdminFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AdminFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		Object attribute = session.getAttribute("isAdmin");

		System.out.println(req.getServletPath());
		System.out.println(attribute);
		String servletPath = req.getServletPath();

		switch (servletPath.trim()) {
		case "/AdminArticle":
			if (attribute != null) {
				boolean parseBoolean = Boolean.parseBoolean(attribute.toString());

				if (parseBoolean) {
					chain.doFilter(request, response);
				} else {
					PrintWriter writer = res.getWriter();
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("code", 999).put("message", "无权限");
					writer.println(jsonObject.toString());
				}
			}
			break;
		case "/Links":
			if (attribute != null) {
				boolean parseBoolean = Boolean.parseBoolean(attribute.toString());
				
				if (parseBoolean) {
					chain.doFilter(request, response);
				} else {
					PrintWriter writer = res.getWriter();
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("code", 999).put("message", "无权限");
					writer.println(jsonObject.toString());
				}
			}
			break;
		case "/Article":
			
			String method = req.getMethod();
			 
			if (method == "GET") {
				chain.doFilter(request, response);
				return;
			} 
			
			if (attribute != null) {
				boolean parseBoolean = Boolean.parseBoolean(attribute.toString());

				if (parseBoolean) {
					chain.doFilter(request, response);
				} else {
					PrintWriter writer = res.getWriter();
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("code", 999).put("message", "无权限");
					writer.println(jsonObject.toString());
				}
			}
			break;

		default:
			chain.doFilter(request, response);
			break;
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
