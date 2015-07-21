package base;

public class JudgeRsp {
	private String id; //oj submit id
	private String reuslt;
	private String detail;
	private String runTime;
	private String runMemory;
	
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReuslt() {
		return reuslt;
	}
	public void setReuslt(String reuslt) {
		this.reuslt = reuslt;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getRunTime() {
		return runTime;
	}
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	public String getRunMemory() {
		return runMemory;
	}
	public void setRunMemory(String runMemory) {
		this.runMemory = runMemory;
	}
	
	
}
