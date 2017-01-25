package entity;

public class User {
	private String usernum;
	private String username;
	private String password;
	private String  realname;
	private int userType;
	private int sex;
	
	public User() {
		super();
	}

	public User(String usernum,String username, String password, String realname, int userType,
			int sex) {
		super();
		this.usernum=usernum;
		this.username = username;
		this.password = password;
		this.realname = realname;
		this.userType = userType;
		this.sex = sex;
	}
	public String getUsernum() {
		return usernum;
	}

	public void setUsernum(String usernum) {
		this.usernum = usernum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String usrname) {
		this.username = usrname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
	
	
	

}
