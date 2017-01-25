package entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBBean;

public class ReportMgr {

	private HashMap<String, Report> reportList;
	public ReportMgr() {
		super();
		
	}

	/**
	 * 得到所有用户的列表
	 * 
	 * @return
	 */
	public HashMap getReportList() {
		HashMap reportList = new HashMap();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		Report report = null;
		try {
			conn = DBBean.getConnection();
			String sql = "select * from Table_report";
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				report = new Report(rset.getInt("reportnum"),rset.getInt("rnum"), 
						rset.getString("reason"), rset.getString("reporter"),rset.getString("reportdate"),rset.getInt("pass"));

				//taskList.add(task);
				reportList.put(rset.getString("rnum"), report);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return reportList;
	}

	public int addReport(Report newReport) {
		int result = 0; //
		String sql = "insert into Table_report(rnum,reason,reporter,reportdate,pass)values('"
					+ newReport.getResourcenum()
					+ "','"
					+ newReport.getReason()
					+ "','"
					+ newReport.getReporter()
					+ "','"
					+ newReport.getReportdate()
					+ "','"
					+ newReport.getPass() + "')";

		System.out.println(sql);
		if (DBBean.update(sql)) {
			result = 2; //
		}
		
		return result;
	}
//	public boolean findReport(Report report) {
//		boolean result = false;
//		String sql = "select * from Table_report where report=('"
//				+ task.getTaskname() + "')";
//		result = DBBean.hasRecord(sql);
//		return result;
//	}

	public boolean deleteReport(int reportnum) {
		boolean result = false;
		String sql = "delete from Table_report where reportnum=('" + reportnum
				+ "')";
		result = DBBean.delete(sql);
		System.out.println("delete report:" + sql);
		return result;
	}

	public Report getReport(int reportnum) {
		String sql = "select * from Table_report where reportnum=('" + reportnum
				+ "')";
		Report report = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			conn = DBBean.getConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			int i = 0;
			while (rset.next()) {
				report = new Report(rset.getInt("reportnum"),rset.getInt("rnum"),
						rset.getString("reason"), rset.getString("reporter"),rset.getString("reportdate"),rset.getInt("pass"));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBBean.clean(conn, stmt, rset);
		}
		return report;
	}

	public int editReport(Report report) {
		int result = 1;
		String sql = "update Table_report set rnum ='" + report.getResourcenum()
				+ "',reason='" + report.getReason() 
				+ "',reporter='"+ report.getReporter()
				+ "',reportdate='"+ report.getReportdate()
				+ "',pass='"+ report.getPass()
				+ "' where reportnum='"
				+ report.getReportnum() + "'";
		System.out.println("edit report:" + sql);
		if (DBBean.update(sql)) {
			result = 2; 
		}

		return result;
	}

	
//	public int verifyUser(String username, String password) {
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rset = null;
//		int result = -1;// 用户名密码不对
//		try {
//			conn = DBBean.getConnection();
//			String sql = "select * from table_user where username='" + username
//					+ "' and password='" + password + "'";
//			System.out.println("sql: " + sql);
//			stmt = conn.createStatement();
//			rset = stmt.executeQuery(sql);
//			if (rset.next()) {
//				result = rset.getInt("userType");
//				System.out.println("user type: " + result);
//			}
//		} catch (SQLException e) {
//			System.out.println("SQLException inside verify user");
//			e.printStackTrace();
//
//		} finally {
//			try {
//				rset.close();
//				stmt.close();
//				conn.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}

}
