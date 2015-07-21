package event;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kernel.Judge;
import DB.Contest;
import DB.ContestORM;
import DB.Problem;
import DB.ProblemORM;
import DB.TaskInfoORM;
import DB.UserDoTaskORM;
import DB.UserORM;
import base.JudgeListener;
import base.JudgeReq;
import base.OnlineJudge;
import base.Task;

public class SubmitTask extends Task {
	private int smt_id;
	private int errno = 0;
	private String user_id;
	private int prb_idx;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		int prb_id = Integer.parseInt(request.getParameter("prb_id"));
		
		Problem prob = ProblemORM.getInstance().LoadInfoByPrbId(prb_id);
		String code = request.getParameter("code");
		String OJ = prob.getOj_name();
		String language = request.getParameter("language");
		String submit_params = prob.getSubmit_params();
		Date submitTime = new Date(System.currentTimeMillis());
				
		user_id = (String)request.getSession().getAttribute("handle");
		
		if(user_id==null){
			errno = 1;
			return ;
		}
		
		if( code.length()<51 || code.length()>32767){
			errno = 3;
			return ;
		}
		
		JudgeReq req=new JudgeReq();
		req.setUserId(user_id);
		req.setCode(code);
		req.setOJ(OJ);
		req.setLanguage(language);
		req.setPrb_id(prb_id);
		req.setSubmitTime(submitTime);
		req.setSubmit_params(submit_params);	
		
		if(request.getParameter("prb_idx")!=null)
			prb_idx = Integer.parseInt(request.getParameter("prb_idx"));
		
		int smt_type = Integer.parseInt(request.getParameter("type"));
		JudgeListener l = null;
		
		switch(smt_type){
			case 0:
				int cot_id=Integer.parseInt(request.getParameter("undetermined_id"));
				Contest c =  ContestORM.getInstance().findById(cot_id);
	
				if(c.getEnd_time().before(new Date(System.currentTimeMillis()))){
					errno = 2;
					return ;
				}
				
				l = this.submit_to_contest(cot_id);
				break;
			case 1:
				int task_id=Integer.parseInt(request.getParameter("undetermined_id"));
				l = this.submit_to_task(task_id);
				break;
			case 2:
				l = this.submit_to_section();
				break;
		}
		
		req.setJudgeListener(l);
		
		smt_id=Judge.getInstance().judge(req);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		switch(errno){
			case 1: response.sendRedirect("./index.jsp");
			case 2: response.getWriter().print("The contest has been over.");
			case 3: response.getWriter().print("Your code length should be longer than 50 and not exceed 32767 Bytes");
		}
	}
	
	private JudgeListener submit_to_contest(final int cot_id){
		return new JudgeListener(){
			@Override
			public void onEndJudge(String result) {
				ContestORM.getInstance().addContestSubmit(cot_id, smt_id);
			}
			
		};
	}
	
	private JudgeListener submit_to_task(final int task_id){
		return new JudgeListener(){
			@Override
			public void onEndJudge(String result) {
				TaskInfoORM.getInstance().insertTaskSubmitRecord(task_id, smt_id);
				
				if(result.equals(OnlineJudge.AC)){
					int status = UserDoTaskORM.getInstance().loadStatus(task_id, user_id);
					status = status | (1<<prb_idx);
					status = status + (1<<26);
					TaskInfoORM.getInstance().updateDoTaskStatus(task_id, user_id, status);
				}			
			}
			
		};
	}
	
	private JudgeListener submit_to_section(){
		return new JudgeListener(){
			@Override
			public void onEndJudge(String result) {
				TaskInfoORM.getInstance().insertTaskSubmitRecord(0, smt_id);
				
				if(result.equals(OnlineJudge.AC)){
					int status = UserDoTaskORM.getInstance().loadStatus(0, user_id);
					status = status | (1<<prb_idx);
					TaskInfoORM.getInstance().updateDoTaskStatus(0, user_id, status);
					if(status == ((1<<12)-1)){
						UserORM.getInstance().updPermission(user_id, 1);
					}
				}			
			}			
		};
	}
}
