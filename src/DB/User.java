package DB;

import common.StringTool;

public class User {
	private String user_id;
	private int user_type;
	private String user_psw;
	private String user_name;
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getUser_type() {
		return user_type;
	}
	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}
	public String getUser_psw() {
		if(user_psw==null || user_psw.equals("")) return "";
		return StringTool.hash_MD5(user_psw);
	}
	public void setUser_psw(String user_psw) {
		this.user_psw = user_psw;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	
}
