package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Report;
import entity.ReportMgr;
import entity.User;
import entity.UserMgr;

/**
 * Servlet implementation class ReportServlet
 */
@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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
		String operation = request.getParameter("operation");
		String resourcenum = request.getParameter("resourcenum");		
		//resourcenum = new String(resourcenum.getBytes("ISO-8859-1"), "UTF-8");
		HttpSession session = request.getSession();		
		ReportMgr reportMgr = new ReportMgr();
		System.out.println(operation);
		if(operation.equals("del")){
			String reportnum=request.getParameter("reportnum");
			if(reportMgr.deleteReport(Integer.parseInt(reportnum))){
				request.getRequestDispatcher("/student/showReport.jsp").forward(request, response);
			}
			else{
				PrintWriter out = response.getWriter();
				out.println("<script> alert('删除不成功');</script>");
			}
		}
		if(operation.equals("pass")){
			String pass=request.getParameter("pass");
			String reportnum=request.getParameter("reportnum");
			Report report=reportMgr.getReport(Integer.parseInt(reportnum));
			if(pass.equals("agree"))
			    report.setPass(1);
			else if(pass.equals("disagree"))
				report.setPass(0);
			if(reportMgr.editReport(report)==2){
				request.getRequestDispatcher("/admin/showReport.jsp").forward(request, response);
			}
			else{
				PrintWriter out = response.getWriter();
				out.println("<script> alert('审核不成功');</script>");
			}
		}
	
		if(operation.equals("add")){
			
			String reason = request.getParameter("reason");
			//reason = new String(reason.getBytes("ISO-8859-1"), "UTF-8");
			//String reportdate = request.getParameter("reportdate");
			Date date=new Date();
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			String reportdate=ft.format(date);
			//reportdate = new String(reportdate.getBytes("ISO-8859-1"), "UTF-8");
			String reporter=(String) session.getAttribute("usernum");
			
			Report report = new Report(Integer.parseInt(resourcenum),reason,reporter,reportdate,-1);
			if(reportMgr.addReport(report) ==2){
				request.getRequestDispatcher("/student/main.jsp").forward(request, response);
			}
			else if(reportMgr.addReport(report) ==1){
				PrintWriter out = response.getWriter();
				request.getRequestDispatcher("/student/addReport.jsp").forward(request, response);
			}
		}
		if(operation.equals("edit")){
			String reportnum=request.getParameter("reportnum");
			String reason=request.getParameter("reason");
			//reason = new String(reason.getBytes("ISO-8859-1"), "UTF-8");
			//String reportdate = request.getParameter("reportdate");
			Date date=new Date();
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			String reportdate=ft.format(date);
			//reportdate = new String(reportdate.getBytes("ISO-8859-1"), "UTF-8");
			String reporter=(String) session.getAttribute("usernum");
			
			Report report = new Report(Integer.parseInt(reportnum),Integer.parseInt(resourcenum),reason,reporter,reportdate,-1);
			if(reportMgr.editReport(report) ==2){
				request.getRequestDispatcher("/student/main.jsp").forward(request, response);
			}
			else if(reportMgr.editReport(report) ==1){
				PrintWriter out = response.getWriter();
				request.getRequestDispatcher("/student/editReport.jsp?reportnum=" + reportnum).forward(request, response);
			}
		}
	}

}
