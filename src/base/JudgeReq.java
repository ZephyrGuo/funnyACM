package base;

import java.util.Date;

public class JudgeReq {
	private String userId;
	private String code;
	private String OJ;
	private String language;
	private int prb_id;
	private String submit_params;
	private Date submitTime;
	private JudgeListener judgeListener;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOJ() {
		return OJ;
	}
	public void setOJ(String oJ) {
		OJ = oJ;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public JudgeListener getJudgeListener() {
		return judgeListener;
	}
	public void setJudgeListener(JudgeListener judgeListener) {
		this.judgeListener = judgeListener;
	}
	public String getSubmit_params() {
		return submit_params;
	}
	public void setSubmit_params(String submit_params) {
		this.submit_params = submit_params;
	}
	public int getPrb_id() {
		return prb_id;
	}
	public void setPrb_id(int prb_id) {
		this.prb_id = prb_id;
	}
	
}
