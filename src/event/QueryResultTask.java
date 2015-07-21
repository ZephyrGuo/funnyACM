package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kernel.ResultCache;
import base.JudgeRsp;
import base.Task;

/*
 * desert
 * 
 */

public class QueryResultTask extends Task {

	private JudgeRsp rsp;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		
		int smt_id=Integer.valueOf(request.getParameter("smt_id"));
		rsp=ResultCache.getInstance().getAndRemove(smt_id);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		if(rsp!=null){
			
		}else{
			
		}
	}

}
