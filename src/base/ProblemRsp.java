package base;

public class ProblemRsp {
	private String title;
	private String timeLimit;
	private String memoryLimit;
	private String problemDetail;
	private String inputDetail;
	private String outputDetail;
	private String sampleInput;
	private String sampleOutput;
	private String hintDetail;
	private String submit_params;
	
	
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
