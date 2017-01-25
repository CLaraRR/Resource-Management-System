package entity;

public class Report {
	private int reportnum;
	private int resourcenum;
	private String reason;
	private String reporter;
	private String reportdate;
	private int pass;//-1为待审核，1为同意举报，0为不同意举报
	
	public Report(){
		super();
	}
	
	public Report(int reportnum,int resourcenum,String reason,String reporter,String reportdate,int pass){
		this.reportnum=reportnum;
		this.resourcenum=resourcenum;
		this.reason=reason;
		this.reporter=reporter;
		this.reportdate=reportdate;
		this.pass=pass;
	}
	
	public Report(int resourcenum,String reason,String reporter,String reportdate,int pass){
		this.resourcenum=resourcenum;
		this.reason=reason;
		this.reporter=reporter;
		this.reportdate=reportdate;
		this.pass=pass;
	}
	
	public int getReportnum(){
		return reportnum;
	}
	
	public void setReportnum(int reportnum){
		this.reportnum=reportnum;
	}
	
	public int getResourcenum(){
		return resourcenum;
	}
	
	public void setResourcenum(int resourcenum){
		this.resourcenum=resourcenum;
	}
	
	public String getReason(){
		return reason;
	}
	
	public void setReason(String reason){
		this.reason=reason;
	}
	
	
	public String getReporter(){
		return reporter;
	}
	
	public void setReporter(String reporter){
		this.reporter=reporter;
	}
	
	public String getReportdate(){
		return reportdate;
	}
	
	public void setReportdate(String reportdate){
		this.reportdate=reportdate;
	}
	
	public int getPass(){
		return pass;
	}
	
	public void setPass(int pass){
		this.pass=pass;
	}
}
