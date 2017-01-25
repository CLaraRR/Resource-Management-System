package entity;

public class Task {
	private int tasknum;
	private String taskname;
    private String taskdes;
    private String taskdeadline;
    private String taskauthor;
    
    public Task() {
		super();
	}

	public Task(int tasknum,String taskname, String taskdes, String taskdeadline,String taskauthor) {
		super();
		this.tasknum=tasknum;
		this.taskname = taskname;
		this.taskdes = taskdes;
		this.taskdeadline = taskdeadline;
		this.taskauthor=taskauthor;
	}
	
	public Task(String taskname, String taskdes, String taskdeadline,String taskauthor) {
		super();
		this.taskname = taskname;
		this.taskdes = taskdes;
		this.taskdeadline = taskdeadline;
		this.taskauthor=taskauthor;
	}
	public int getTasknum() {
		return tasknum;
	}

	public void setTasknum(int tasknum) {
		this.tasknum = tasknum;
	}
	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	
	public String getTaskdes() {
		return taskdes;
	}

	public void setTaskdes(String taskdes) {
		this.taskdes = taskdes;
	}
	public String getTaskdeadline() {
		return taskdeadline;
	}

	public void setTaskdeadline(String taskdeadline) {
		this.taskdeadline = taskdeadline;
	}
	public String getTaskauthor() {
		return taskauthor;
	}

	public void setTaskauthor(String taskauthor) {
		this.taskauthor = taskauthor;
	}
}
