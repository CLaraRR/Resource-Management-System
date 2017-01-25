package entity;

public class Resource {
	private int resourcenum;
	private String resourcename;
    private String resourcedes;
    private String resourcedate;
    private String resourceauthor;
    
    public Resource() {
		super();
	}

	public Resource(int resourcenum,String resourcename, String resourcedes,String resourcedate,String resourceauthor) {
		super();
		this.resourcenum=resourcenum;
		this.resourcename = resourcename;
		this.resourcedes = resourcedes;
		this.resourcedate=resourcedate;
		this.resourceauthor=resourceauthor;
	}
	
	public Resource(String resourcename, String resourcedes,String resourcedate,String resourceauthor) {
		super();
		this.resourcename = resourcename;
		this.resourcedes = resourcedes;
		this.resourcedate=resourcedate;
		this.resourceauthor=resourceauthor;
	}

	public int getResourcenum() {
		return resourcenum;
	}

	public void setResourcenum(int resourcenum) {
		this.resourcenum = resourcenum;
	}
	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}
	
	public String getResourcedes() {
		return resourcedes;
	}

	public void setResourcedes(String resourcedes) {
		this.resourcedes = resourcedes;
	}
	public String getResourcedate() {
		return resourcedate;
	}

	public void setResourcedate(String resourcedate) {
		this.resourcedate = resourcedate;
	}


	public String getResourceauthor() {
		return resourceauthor;
	}

	public void setResourceauthor(String resourceauthor) {
		this.resourceauthor = resourceauthor;
	}
}
