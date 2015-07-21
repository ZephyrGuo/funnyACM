package kernel;

import DB.Problem;
import base.OnlineJudge;
import base.ProblemRsp;

public class CaptureProblem {
	
	private static CaptureProblem instance;
	
	private CaptureProblem(){
		
	}
	
	public static CaptureProblem getInstance(){
		if(instance==null) instance=new CaptureProblem();
		return instance;
	}
	
	
	public Problem capture(String oj_name,String oj_id){
		OnlineJudge oj=this.getOjByName(oj_name);
		ProblemRsp rsp=oj.linkProblem(oj_id);
		
		if(rsp==null) return null;
		
		Problem prob=new Problem();
		prob.parse(rsp);
		prob.setOj_id(oj_id);
		prob.setOj_name(oj_name);
		
		return prob;
	}
	
	
	private OnlineJudge getOjByName(String name){
		try {
			return (OnlineJudge)Class.forName("oj."+name).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
