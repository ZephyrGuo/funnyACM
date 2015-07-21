package base;

import java.util.Map;
import java.util.Properties;
import common.Configuration;

public abstract class OnlineJudge {

	public static final String AC  = "Accepted";
	public static final String PE  = "Presentation Error";
	public static final String WA  = "Wrong Answer";
	public static final String TLE = "Time Limit Exceeded";
	public static final String MLE = "Memory Limit Exceeded";
	public static final String RE  = "Runtime Error";
	public static final String OLE = "Output Limit Exceeded";
	public static final String CE = "Compilation Error";
	public static final String JE = "Judge Error";
	
	protected Account[] accounts;

	public OnlineJudge(String confPath){
		this.refresh(confPath);
	}
			
	public void refresh(String confPath){
		Properties f = Configuration.load(confPath);
		
		String value=f.getProperty("accounts");
		String[] user=value.split(";");
		value=f.getProperty("password");
		String[] psw=value.split(";");
		value=f.getProperty("interval");
		
		accounts=new Account[user.length];
		for(int i=0;i<user.length;i++){
			accounts[i]=new Account(user[i],psw[i],Integer.parseInt(value));
		}
	}
	
	public Account chooseAccout(){
		for(int i=0;i<accounts.length;i=(i+1)%accounts.length){
			if(accounts[i].usable()) return accounts[i];
		}
		return null;
	}
	
	abstract protected Map<String, String> login(Account account);
	
	abstract public boolean submit(SubmitReq sr);
	
	abstract public JudgeRsp takeResult(Account account);
	
	abstract public ProblemRsp linkProblem(String problemId);
	
	abstract public boolean isJudging(JudgeRsp rsp);
	
	abstract public String resConvert(String res);

}
