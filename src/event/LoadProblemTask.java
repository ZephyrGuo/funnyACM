package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Problem;
import DB.ProblemORM;
import base.Task;

public class LoadProblemTask extends Task {
	private Problem prob;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		int prb_id;
		try{
			prb_id=Integer.parseInt(request.getParameter("prb_id"));
		}catch(Exception e){
			prob = null;
			return ;
		}
		prob=ProblemORM.getInstance().LoadDetailByPrbId(prb_id);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		

	}

	public Problem getProblem(){
		return prob;
	}
}
