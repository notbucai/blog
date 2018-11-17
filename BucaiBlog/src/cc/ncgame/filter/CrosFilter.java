package cc.ncgame.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CrosFilter
 */
@WebFilter("/CrosFilter")
public class CrosFilter implements Filter {


	/**
	 * Default constructor.
	 */
	public CrosFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		
		String origin = req.getHeader("Origin");
		
		res.addHeader("Access-Control-Allow-Origin", origin);
		res.addHeader("Access-Control-Allow-Credentials", "true");
//		Access-Control-Allow-Credentials
		res.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
		res.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,content-length");
		res.setCharacterEncoding("UTF-8");
//		res.setStatus(404);
		res.setContentType("application/json;charset=UTF-8");
		
		chain.doFilter(request, response);
		
		System.err.println("doFilter to");
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
