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


public class UserServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserServlet() {
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

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//response.setContentType("text/html");
		String operation = request.getParameter("operation");
		String username = request.getParameter("username");		
		//username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		HttpSession session = request.getSession();		
		UserMgr userMgr = new UserMgr();
		if(operation.equals("del")){
			if(userMgr.deleteUser(username)){
				request.getRequestDispatcher("/admin/main.jsp").forward(request, response);
			}
			else{
				PrintWriter out = response.getWriter();
				out.println("<script> alert('删除不成功');</script>");
			}
		}
		if(operation.equals("add")){
			String usernum=request.getParameter("usernum");
			 //usernum = new String(usernum.getBytes("ISO-8859-1"), "UTF-8");
			String userType = request.getParameter("userType");
			//userType = new String(userType.getBytes("ISO-8859-1"), "UTF-8");
			String realname = request.getParameter("realname");
			//realname = new String(realname.getBytes("ISO-8859-1"), "UTF-8");
			String sex = request.getParameter("sex");
			//sex = new String(sex.getBytes("ISO-8859-1"), "UTF-8");
			System.out.println(usernum+" "+username+" "+userType+" "+realname+" "+sex);
			User user = new User(usernum,username,"888888",realname,Integer.parseInt(userType),Integer.parseInt(sex));
			if(userMgr.addUser(user) ==2){
				request.getRequestDispatcher("/admin/main.jsp").forward(request, response);
			}
			else if(userMgr.addUser(user) ==1){
				PrintWriter out = response.getWriter();
				request.getRequestDispatcher("/admin/addUser.jsp").forward(request, response);
			}
		}
		if(operation.equals("edit")){
			String usernum=request.getParameter("usernum");
			//usernum = new String(usernum.getBytes("ISO-8859-1"), "UTF-8");
			String userType = request.getParameter("userType");
			//userType = new String(userType.getBytes("ISO-8859-1"), "UTF-8");
			String realname = request.getParameter("realname");
			//realname = new String(realname.getBytes("ISO-8859-1"), "UTF-8");
			String sex = request.getParameter("sex");
			//sex = new String(sex.getBytes("ISO-8859-1"), "UTF-8");
			System.out.println("username=" + username);
			User user = new User(usernum,username,"888888",realname,Integer.parseInt(userType),Integer.parseInt(sex));
			if(userMgr.editUser(user) ==2){
				request.getRequestDispatcher("/admin/main.jsp").forward(request, response);
			}
			else if(userMgr.editUser(user) ==1){
				PrintWriter out = response.getWriter();
				request.getRequestDispatcher("/admin/editUser.jsp?username=" + username).forward(request, response);
			}
		}
	}
}