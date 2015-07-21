package base;

public class SubmitReq {
	private String code;
	private String language;
	private Account account;
	private String submit_params;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getSubmit_params() {
		return submit_params;
	}
	public void setSubmit_params(String submit_params) {
		this.submit_params = submit_params;
	}
	
}
