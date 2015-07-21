package kernel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import DB.SubmitRecordORM;
import base.Account;
import base.JudgeReq;
import base.JudgeRsp;
import base.OnlineJudge;
import base.SubmitReq;

class OJMainThread implements Runnable{
	private OnlineJudge oj;
	private BlockingQueue<JudgeInfo> que;
	private ExecutorService pool;
	private boolean run;
	
	public OJMainThread(OnlineJudge oj,ExecutorService pool){
		this.oj=oj;
		this.pool=pool;
		run=false;
		que=new LinkedBlockingQueue<JudgeInfo>();
	}
	
	public void open(){
		run=true;
	}
	
	public void close(){
		run=false;
	}
	
	public int addRequest(JudgeReq req){
		try {
			JudgeInfo info=new JudgeInfo();
			info.setReq(req);
			info.setSmt_id(SubmitRecordORM.getInstance().getSmt_id());
			que.put(info);
			return info.getSmt_id();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public void run() {
		while(run){		
			Account ac=oj.chooseAccout();
			ac.lock();
			JudgeInfo info;
			try {
				info = que.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
				run=false;
				break;
			}
			JudgeTask task=new JudgeTask(oj,info.getSmt_id(),info.getReq(),ac);		
			pool.execute(task);
		}
	}
	
	private class JudgeInfo{
		private JudgeReq req;
		private int smt_id;
		
		
		public JudgeReq getReq() {
			return req;
		}
		public void setReq(JudgeReq req) {
			this.req = req;
		}
		public int getSmt_id() {
			return smt_id;
		}
		public void setSmt_id(int smt_id) {
			this.smt_id = smt_id;
		}
	}
}