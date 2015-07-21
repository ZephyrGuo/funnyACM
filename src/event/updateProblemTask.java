package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Problem;
import DB.ProblemORM;
import base.Task;

public class updateProblemTask extends Task {
	private int errno = 0;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String title = request.getParameter("title");
		String problemDetail = request.getParameter("problemDetail");
		String inputDetail = request.getParameter("inputDetail");
		String outputDetail = request.getParameter("outputDetail");
		String sampleInput = request.getParameter("sampleInput");
		String sampleOutput = request.getParameter("sampleOutput");
		String hintDetail = request.getParameter("hintDetail");
		int prb_id = Integer.parseInt(request.getParameter("prb_id"));
		
		Problem prb = new Problem();
		
		prb.setPrb_id(prb_id);
		prb.setTitle(title);
		prb.setProblemDetail(problemDetail);
		prb.setInputDetail(inputDetail);
		prb.setOutputDetail(outputDetail);
		prb.setSampleInput(sampleInput);
		prb.setSampleOutput(sampleOutput);
		prb.setHintDetail(hintDetail);
		
		if(!ProblemORM.getInstance().update(prb)){
			errno = 1;
		}
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		if(errno==1){
			response.getWriter().print("更新出错，请查看错误日志");
		}else{
			response.getWriter().print("Success!");
		}
	}


}
