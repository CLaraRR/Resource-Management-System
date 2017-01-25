package entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBBean;

public class TaskMgr {

	private HashMap<String, Task> taskList;
	public TaskMgr() {
		super();
		
	}

	/**
	 * 得到所有用户的列表
	 * 
	 * @return
	 */
	public HashMap getTaskList() {
		HashMap taskList = new HashMap();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		Task task = null;
		try {
			conn = DBBean.getConnection();
			String sql = "select * from Table_task";
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				task = new Task(rset.getInt("tasknum"),rset.getString("taskname"), 
						rset.getString("taskdes"), rset.getString("taskdeadline"),rset.getString("taskauthor"));

				//taskList.add(task);
				taskList.put(rset.getString("taskname"), task);
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
		return taskList;
	}

	public int addTask(Task newTask) {
		int result = 0; //
		if (findTask(newTask)) {
			result = 1; //
		} else {
			String sql = "insert into Table_task(taskname,taskdes,taskdeadline,taskauthor)values('"
					+ newTask.getTaskname()
					+ "','"
					+ newTask.getTaskdes()
					+ "','"
					+ newTask.getTaskdeadline()
					+ "','"
					+ newTask.getTaskauthor() + "')";

			System.out.println(sql);
			if (DBBean.update(sql)) {
				result = 2; //
			}
		}
		return result;
	}
	public boolean findTask(Task task) {
		boolean result = false;
		String sql = "select * from Table_task where taskname=('"
				+ task.getTaskname() + "')";
		result = DBBean.hasRecord(sql);
		return result;
	}

	public boolean deleteTask(int tasknum) {
		boolean result = false;
		String sql = "delete from Table_task where tasknum=('" + tasknum
				+ "')";
		result = DBBean.delete(sql);
		System.out.println("delete task:" + sql);
		return result;
	}

	public Task getTask(int tasknum) {
		String sql = "select * from Table_task where tasknum=('" + tasknum
				+ "')";
		Task task = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			conn = DBBean.getConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			int i = 0;
			while (rset.next()) {
				task = new Task(rset.getInt("tasknum"),rset.getString("taskname"),
						rset.getString("taskdes"), rset.getString("taskdeadline"),rset.getString("taskauthor"));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBBean.clean(conn, stmt, rset);
		}
		return task;
	}

	public int editTask(Task task) {
		int result = 1;
		String sql = "update Table_task set taskname ='" + task.getTaskname()
				+ "',taskdes='" + task.getTaskdes() + "',taskdeadline='"	
				+ task.getTaskdeadline()
				+ "' where tasknum='"
				+ task.getTasknum() + "'";
		System.out.println("edit task:" + sql);
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
