package servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class TestFilter
 */
public class RequestFilter implements Filter {

    /**
     * Default constructor. 
     */
    public RequestFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			//System.out.println("@@@@@");
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
            //System.out.print("Set encoding");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
