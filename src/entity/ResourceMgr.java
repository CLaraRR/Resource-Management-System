package entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBBean;

public class ResourceMgr {

	private HashMap<String, Resource> resourceList;

	public ResourceMgr() {
		super();
	}

	/**
	 * 得到所有用户的列表
	 * 
	 * @return
	 */
	public HashMap<String, Resource> getResourceList() {
		HashMap<String, Resource> resourceList = new HashMap<String, Resource>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		Resource resource = null;
		try {
			conn = DBBean.getConnection();
			String sql = "select * from Table_resource";
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				resource = new Resource(rset.getInt("rnum"),rset.getString("rname"), 
						rset.getString("rdes"), rset.getString("rdate"),rset.getString("rauthor"));

				resourceList.put(rset.getString("rname"), resource);
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
		return resourceList;
	}

	public int addResource(Resource newResource) {
		int result = 0; //
		if (findResource(newResource)) {
			result = 1; //
		} else {
			String sql = "insert into Table_resource(rname,rdes,rdate,rauthor)values('"
					+ newResource.getResourcename()
					+ "','"
					+ newResource.getResourcedes()
					+ "','"
					+ newResource.getResourcedate()
					+ "','"
					+ newResource.getResourceauthor() + "')";

			if (DBBean.update(sql)) {
				result = 2; //
			}
		}
		return result;
	}

	public boolean findResource(Resource resource) {
		boolean result = false;
		String sql = "select * from Table_resource where rname=('"
				+ resource.getResourcename() + "')";
		result = DBBean.hasRecord(sql);
		return result;
	}

	public boolean deleteResource(String resourcenum) {
		boolean result = false;
		String sql = "delete from Table_resource where rnum=('" + resourcenum
				+ "')";
		result = DBBean.delete(sql);
		System.out.println("delete resource:" + sql);
		return result;
	}

	public Resource getResource(int resourcenum) {
		String sql = "select * from Table_resource where rnum=('" + resourcenum
				+ "')";
		Resource resource = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			conn = DBBean.getConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			int i = 0;
			while (rset.next()) {
				resource = new Resource(rset.getInt("rnum"),rset.getString("rname"),
						rset.getString("rdes"), rset.getString("rdate"),rset.getString("rauthor"));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBBean.clean(conn, stmt, rset);
		}
		return resource;
	}

	public int editResource(Resource resource) {
		int result = 1;
		String sql = "update Table_resource set rname ='" + resource.getResourcename()
				+ "',rdes='" + resource.getResourcedes()
				+ "' where rnum='"
				+ resource.getResourcenum() + "'";
		System.out.println("edit resource:" + sql);
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
