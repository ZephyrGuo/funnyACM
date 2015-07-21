package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Problem;
import DB.ProblemORM;
import base.Task;

public class SaveProblemTask extends Task {

	private int errno = 0;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String oj_name = request.getParameter("oj_name");
		String oj_id = request.getParameter("oj_id");
		String title = request.getParameter("title");
		String problemDetail = request.getParameter("problemDetail");
		String inputDetail = request.getParameter("inputDetail");
		String outputDetail = request.getParameter("outputDetail");
		String sampleInput = request.getParameter("sampleInput");
		String sampleOutput = request.getParameter("sampleOutput");
		String hintDetail = request.getParameter("hintDetail");
		String timeLimit = request.getParameter("timeLimit");
		String memoryLimit = request.getParameter("memoryLimit");
		String submit_params = request.getParameter("submit_params");
		
		Problem prb = new Problem();
		
		prb.setOj_name(oj_name);
		prb.setOj_id(oj_id);
		prb.setTitle(title);
		prb.setProblemDetail(problemDetail);
		prb.setInputDetail(inputDetail);
		prb.setOutputDetail(outputDetail);
		prb.setSampleInput(sampleInput);
		prb.setSampleOutput(sampleOutput);
		prb.setHintDetail(hintDetail);
		prb.setTimeLimit(timeLimit);
		prb.setMemoryLimit(memoryLimit);
		prb.setSubmit_params(submit_params);
		
		if(!ProblemORM.getInstance().save(prb)){
			errno = 1;
		}
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		if(errno==1){
			response.getWriter().print("存储时发生错误，请查看错误日志");
		}else{
			response.getWriter().print("Success!");
		}
	}

}
