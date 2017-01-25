package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Task;
import entity.TaskMgr;

/**
 * Servlet implementation class TaskServlet
 */
@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		processRequest(request,response);
	}
	
	
	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session=request.getSession();
		String operation = request.getParameter("operation");
		
		TaskMgr taskMgr = new TaskMgr();
		if(operation.equals("del")){
			String tasknum=request.getParameter("tasknum");
			//tasknum = new String(tasknum.getBytes("ISO-8859-1"), "UTF-8");
			if(taskMgr.deleteTask(Integer.parseInt(tasknum))){
				request.getRequestDispatcher("/teacher/main.jsp").forward(request, response);
			}
			else{
				PrintWriter out = response.getWriter();
				out.println("<script> alert('删除不成功');</script>");
			}
		}
		if(operation.equals("add")){
			String taskname = request.getParameter("taskname");	
			System.out.println(taskname);
			//taskname = new String(taskname.getBytes("ISO-8859-1"), "UTF-8");
			String taskdeadline = request.getParameter("taskdeadline");
			//taskdeadline = new String(taskdeadline.getBytes("ISO-8859-1"), "UTF-8");
			String taskdes = request.getParameter("taskdes");
			//taskdes = new String(taskdes.getBytes("ISO-8859-1"), "UTF-8");
			//System.out.println("userType=" + userType);
			
			String taskauthor=(String) session.getAttribute("usernum");
			System.out.println(taskauthor);
			Task task = new Task(taskname,taskdes,taskdeadline,taskauthor);
			if(taskMgr.addTask(task) ==2){
				request.getRequestDispatcher("/teacher/main.jsp").forward(request, response);			   
				createDir(taskname);//创建该任务对应的文件夹，这个文件夹用来存储学生上传的附件
			}
			else if(taskMgr.addTask(task) ==1){
				request.getRequestDispatcher("/teacher/addTask.jsp").forward(request, response);
			}
		}
		if(operation.equals("edit")){
			String taskname = request.getParameter("taskname");	
			//taskname = new String(taskname.getBytes("ISO-8859-1"), "UTF-8");
			String tasknum=request.getParameter("tasknum");
			//tasknum = new String(tasknum.getBytes("ISO-8859-1"), "UTF-8");
			String taskdeadline = request.getParameter("taskdeadline");
			//taskdeadline = new String(taskdeadline.getBytes("ISO-8859-1"), "UTF-8");
			String taskdes = request.getParameter("taskdes");
			//taskdes = new String(taskdes.getBytes("ISO-8859-1"), "UTF-8");
			String taskauthor=(String) session.getAttribute("usernum");
			Task task = new Task(Integer.parseInt(tasknum),taskname,taskdes,taskdeadline,taskauthor);
			if(taskMgr.editTask(task) ==2){
				request.getRequestDispatcher("/teacher/main.jsp").forward(request, response);
			}
			else if(taskMgr.editTask(task) ==1){
				request.getRequestDispatcher("/teacher/editTask.jsp?taskname=" + taskname).forward(request, response);
			}
		}
	}
	
	private void createDir(String dirname){
		// 构造临时路径来存储上传的文件
		// 这个路径相对当前应用的目录
		String uploadPath = getServletContext().getRealPath("./") + File.separator + "upload"+File.separator+dirname;   
				         
		// 如果目录不存在则创建
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
	}
	

}
