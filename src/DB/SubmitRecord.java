package DB;

import java.sql.Timestamp;

import base.JudgeRsp;

public class SubmitRecord {
	private int smt_id;
	private Timestamp smt_time;
	private String smt_user;
	private String smt_oj;
	private int prb_id;
	private String code;

	private String smt_oj_id;
	private String result;
	private String detail;
	private String runTime;
	private String runMemory;

	public void parse(JudgeRsp rsp) {
		this.smt_oj_id = rsp.getId();
		this.result = rsp.getReuslt();
		this.detail = rsp.getDetail();
		this.runTime = rsp.getRunTime();
		this.runMemory = rsp.getRunMemory();
	}

	public String getSmt_oj_id() {
		return smt_oj_id;
	}

	public void setSmt_oj_id(String smt_oj_id) {
		this.smt_oj_id = smt_oj_id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public int getPrb_id() {
		return prb_id;
	}

	public void setPrb_id(int prb_id) {
		this.prb_id = prb_id;
	}

	public int getSmt_id() {
		return smt_id;
	}

	public void setSmt_id(int smt_id) {
		this.smt_id = smt_id;
	}

	public Timestamp getSmt_time() {
		return smt_time;
	}

	public void setSmt_time(Timestamp smt_time) {
		this.smt_time = smt_time;
	}

	public String getSmt_user() {
		return smt_user;
	}

	public void setSmt_user(String smt_user) {
		this.smt_user = smt_user;
	}

	public String getSmt_oj() {
		return smt_oj;
	}

	public void setSmt_oj(String smt_oj) {
		this.smt_oj = smt_oj;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
