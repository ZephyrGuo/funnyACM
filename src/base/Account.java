package base;

import java.util.Date;

public class Account {
	private String account;
	private String password;
	private boolean lock;
	private int interval;
	private Date lastUse;

	public Account(String account,String password,int interval){
		this.account=account;
		this.password=password;
		this.interval=interval;
		lock=false;
		lastUse=new Date(System.currentTimeMillis()-interval);
	}
	
	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public boolean usable(){
		return !lock && (System.currentTimeMillis()-lastUse.getTime()>interval);
	}
	
	public void lock(){
		if(usable()) lock=true;
	}
	
	public void unlock(){
		lock=false;
	}
	
	public void updLastUseTime(){
		lastUse.setTime(System.currentTimeMillis());
	}
	
}
