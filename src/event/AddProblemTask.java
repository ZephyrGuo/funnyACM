package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 



import org.json.JSONException;
import org.json.JSONStringer;

import kernel.CaptureProblem;
import DB.Problem;
import DB.ProblemORM;
import base.Task;

public class AddProblemTask extends Task {
	String res_json;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String oj_name=request.getParameter("oj_name");
		String oj_id=request.getParameter("oj_id");
		
		Problem prob=new Problem();
		prob.setOj_name(oj_name);
		prob.setOj_id(oj_id);
		
		if(ProblemORM.getInstance().exist(prob)){
			res_json = new JSONStringer().object()
					.key("errno")
					.value("1")
					.endObject()
					.toString();
			return ;
		}
		
		prob=CaptureProblem.getInstance().capture(oj_name, oj_id);
		
		if(prob==null){
			res_json = new JSONStringer().object()
					.key("errno")
					.value("3")
					.endObject()
					.toString();
			return ;
		}
		
		try{
			res_json = new JSONStringer().object()
					.key("title")
					.value(prob.getTitle())
					.key("problemDetail")
					.value(prob.getProblemDetail())
					.key("inputDetail")
					.value(prob.getInputDetail())
					.key("outputDetail")
					.value(prob.getOutputDetail())
					.key("sampleInput")
					.value(prob.getSampleInput())
					.key("sampleOutput")
					.value(prob.getSampleOutput())
					.key("hintDetail")
					.value(prob.getHintDetail())
					.key("timeLimit")
					.value(prob.getTimeLimit())
					.key("memoryLimit")
					.value(prob.getMemoryLimit())
					.key("submit_params")
					.value(prob.getSubmit_params())
					.key("errno")
					.value("0")
					.endObject()    
					.toString(); 
		}catch(JSONException e){
			e.printStackTrace();
			res_json = new JSONStringer().object()
					.key("errno")
					.value("2")
					.endObject()
					.toString();
		}	
	}	

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		response.getWriter().print(res_json);
	}

}
