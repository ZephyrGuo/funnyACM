package DB;

import base.ProblemRsp;

public class Problem {
	
	// problem_info
	private int prb_id;
	private String oj_id;
	private String oj_name;
	private String submit_params;
	
	//problem_detail
	private String title;
	private String timeLimit;
	private String memoryLimit;
	private String problemDetail;
	private String inputDetail;
	private String outputDetail;
	private String sampleInput;
	private String sampleOutput;
	private String hintDetail;
	
	
	public void parse(ProblemRsp rsp){
		this.title=rsp.getTitle();
		this.timeLimit=rsp.getTimeLimit();
		this.memoryLimit=rsp.getMemoryLimit();
		this.problemDetail=rsp.getProblemDetail();
		this.inputDetail=rsp.getInputDetail();
		this.outputDetail=rsp.getOutputDetail();
		this.sampleInput=rsp.getSampleInput();
		this.sampleOutput=rsp.getSampleOutput();
		this.hintDetail=rsp.getHintDetail();
		this.submit_params=rsp.getSubmit_params();
	}
	
	public int getPrb_id() {
		return prb_id;
	}
	public void setPrb_id(int prb_id) {
		this.prb_id = prb_id;
	}
	public String getOj_id() {
		return oj_id;
	}
	public void setOj_id(String oj_id) {
		this.oj_id = oj_id;
	}
	public String getOj_name() {
		return oj_name;
	}
	public void setOj_name(String oj_name) {
		this.oj_name = oj_name;
	}
	public String getSubmit_params() {
		return submit_params;
	}
	public void setSubmit_params(String submit_params) {
		this.submit_params = submit_params;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getMemoryLimit() {
		return memoryLimit;
	}
	public void setMemoryLimit(String memoryLimit) {
		this.memoryLimit = memoryLimit;
	}
	public String getProblemDetail() {
		return problemDetail;
	}
	public void setProblemDetail(String problemDetail) {
		this.problemDetail = problemDetail;
	}
	public String getInputDetail() {
		return inputDetail;
	}
	public void setInputDetail(String inputDetail) {
		this.inputDetail = inputDetail;
	}
	public String getOutputDetail() {
		return outputDetail;
	}
	public void setOutputDetail(String outputDetail) {
		this.outputDetail = outputDetail;
	}
	public String getSampleInput() {
		return sampleInput;
	}
	public void setSampleInput(String sampleInput) {
		this.sampleInput = sampleInput;
	}
	public String getSampleOutput() {
		return sampleOutput;
	}
	public void setSampleOutput(String sampleOutput) {
		this.sampleOutput = sampleOutput;
	}
	public String getHintDetail() {
		return hintDetail;
	}
	public void setHintDetail(String hintDetail) {
		this.hintDetail = hintDetail;
	}

}
