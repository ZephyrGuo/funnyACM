package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONStringer;

import DB.Problem;
import DB.ProblemORM;
import base.Task;

public class QueryProblemTask extends Task {
	
	private int errno = 0;
	private String errmsg;
	private String json;
	
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {		
		String problem = request.getParameter("problem");
		
		if(!problem.matches("[A-Z]+\\.\\d+")){
			errno = 1;
			errmsg = new String("题目格式错误");
			return ;
		}
		
		String t[] = problem.split("\\.");
		
		Problem p = ProblemORM.getInstance().LoadInfoByOJ(t[0], t[1]);
		
		if(p==null){
			errno = 2;
			errmsg = new String("不存在此题");
			return ;
		}
		
		json = new JSONStringer().object()
				.key("errno")
				.value(0)
				.key("prb_id")
				.value(p.getPrb_id())
				.endObject()
				.toString();		
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		
		if(errno!=0){
			json = new JSONStringer().object()
					.key("errno")
					.value(errno)
					.key("errmsg")
					.value(errmsg)
					.endObject()
					.toString();	
		}
		
		response.getWriter().print(json);
	}

}