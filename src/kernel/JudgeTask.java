package kernel;

import java.sql.Timestamp;
import java.util.Properties;

import common.Configuration;
import common.ThreadTool;
import DB.SubmitRecord;
import DB.SubmitRecordORM;
import base.Account;
import base.JudgeReq;
import base.JudgeRsp;
import base.OnlineJudge;
import base.SubmitReq;

class JudgeTask implements Runnable{
	private OnlineJudge oj;
	private JudgeReq req;
	private Account ac;
	private int smt_id;
	private static int limit=-1;
	
	public JudgeTask(OnlineJudge oj, int smt_id, JudgeReq req, Account ac) {
		this.oj = oj;
		this.req = req;
		this.ac = ac;
		this.smt_id=smt_id;
		if(JudgeTask.limit<0){
			JudgeTask.limit=JudgeTask.readLimit();
		}
	}

	@Override
	public void run() {
		SubmitReq sr=new SubmitReq();
		sr.setAccount(ac);
		sr.setCode(req.getCode());
		sr.setLanguage(req.getLanguage());
		sr.setSubmit_params(req.getSubmit_params());
						
		SubmitRecord smt=new SubmitRecord();
		smt.setCode(req.getCode());
		smt.setSmt_id(smt_id);
		smt.setSmt_oj(req.getOJ());
		smt.setPrb_id(req.getPrb_id());
		smt.setSmt_time(new Timestamp(req.getSubmitTime().getTime()));
		smt.setSmt_user(req.getUserId());
		
		JudgeRsp rsp = null;
		
		if(oj.submit(sr)){
			// take result			
			int cnt=0;
			do{
				if(cnt>=JudgeTask.limit) break; //time out
				if(rsp!=null){
					ThreadTool.sleep(1000); //wait 1000ms
					cnt++;
				}
				rsp=oj.takeResult(ac);
			}while(oj.isJudging(rsp));
		}else{
			rsp = new JudgeRsp();
			rsp.setReuslt(OnlineJudge.JE);
		}
		
		ac.unlock();
		
		rsp.setReuslt( oj.resConvert(rsp.getReuslt()) );
		
		if(rsp.getReuslt().equals(OnlineJudge.JE)){
			rsp.setDetail("");
			rsp.setId("none");
			rsp.setRunMemory("0");
			rsp.setRunTime("0");
		}	
		
		// add to cache
//		ResultCache.getInstance().add(smt.getSmt_id(),rsp);
		
		//save
		smt.parse(rsp);
		if(!SubmitRecordORM.getInstance().save(smt)){
			System.out.println("Error:save submit record failed.");
		}else{
			req.getJudgeListener().onEndJudge(rsp.getReuslt());
		}
	}
		
	private static int readLimit(){
		Properties conf=Configuration.load("conf/common.properties");
		return Integer.parseInt(conf.getProperty("judge_limit"));
	}
	
}
