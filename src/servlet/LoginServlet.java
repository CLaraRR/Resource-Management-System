package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import entity.UserMgr;

public class LoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request,response);
	
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String usernum = request.getParameter("usernum");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		//System.out.println("username=" + username);
		//System.out.println("password=" + password);
		UserMgr userMgr = new UserMgr();
		int result = -2;
		result = userMgr.verifyUser(usernum, password);
		User user=userMgr.getUser(usernum);
		//System.out.println(user.getUsername());
		String priv = "" + result;
		switch (result) {
		case -2://
			request.getRequestDispatcher("/index.jsp?error=密码错误").forward(request, response);
			
			break;
		case -1://用户名或密码错误
			request.getRequestDispatcher("/index.jsp?error=账号错误").forward(request, response);
			break;
		case 0://学生用户
			session.setAttribute("priv", priv);
			session.setAttribute("usernum", user.getUsernum());
			request.getRequestDispatcher("/student/main.jsp").forward(request, response);
			break;
		case 1://教师用户
			session.setAttribute("priv", priv);
			session.setAttribute("usernum",user.getUsernum());
			request.getRequestDispatcher("/teacher/main.jsp").forward(request, response);
			break;
		case 2://管理员用户
			session.setAttribute("priv", priv);
			session.setAttribute("usernum", user.getUsernum());
			request.getRequestDispatcher("/admin/main.jsp").forward(request, response);
			
		}
		
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request,response);
		
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
