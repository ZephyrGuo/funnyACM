package kernel;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import common.ThreadTool;
import base.JudgeReq;
import base.JudgeRsp;

public class ResultCache {
	private static ResultCache instance;
	private Map<Integer,JudgeRsp> cache;
	private FixedCache fixed;
	
	private ResultCache(){
		cache=new HashMap<Integer,JudgeRsp>();
		fixed=new FixedCache();
		ThreadTool.runTask(fixed);
	}
	
	public static ResultCache getInstance(){
		if(instance==null) instance=new ResultCache();
		return instance;
	}
	
	public void add(int smt_id, JudgeRsp rsp) {
		cache.put(smt_id, rsp);
		fixed.add(smt_id);
	}
	
	public JudgeRsp getAndRemove(int smt_id){
		JudgeRsp rsp=get(smt_id);
		remove(smt_id);
		return rsp;
	}
	
	private JudgeRsp get(int smt_id){
		return cache.get(smt_id);
	}
	
	private void remove(int smt_id){
		cache.remove(smt_id);
	}
	
	
	
	private class FixedCache implements Runnable{
		private Queue<Integer> que;
		private Queue<Date> putTime;
		
		public FixedCache(){
			que=new LinkedList<Integer>();
			putTime=new LinkedList<Date>();
		}
		
		private void add(int smt_id){
			que.add(smt_id);
			putTime.add(new Date(System.currentTimeMillis()));
		}
		
		private void fixed(){
			while(!que.isEmpty()){
				if((System.currentTimeMillis()-putTime.peek().getTime())>1000*60*2){
					remove(que.poll());
					putTime.poll();
				}else{
					break;
				}
			}
		}
		
		@Override
		public void run() {
			ThreadTool.sleep(1000*60*10); //10 minutes
			fixed();
		}
		
	}
	
}
