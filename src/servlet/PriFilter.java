package servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class PrivControl
 */
public class PriFilter implements Filter {
	private FilterConfig filterConfig = null;
	private static final String[] dir = { "/student",//
			"/teacher",//
			"/admin"//	
	};

	/**
	 * Default constructor.
	 */
	public PriFilter() {
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
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = (req).getSession();
		String user = (String) session.getAttribute("usernum");
		System.out.println("user:"+user);
		String priv = (String) session.getAttribute("priv");
		System.out.println("priv:"+priv);
		String url = req.getRequestURI().substring(
				req.getContextPath().length());
		for (int i = 0; i < dir.length; i++) {
			if (url.startsWith(dir[i])) {
				if (user == null) {
					request.getRequestDispatcher("/index.jsp")
							.forward(request, response);
				} else if (Integer.parseInt(priv) != i) {
					request.getRequestDispatcher("/error.jsp").forward(
							request, response);
				}
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
}
