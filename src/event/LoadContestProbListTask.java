package event;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.Contest;
import DB.ContestORM;
import DB.Problem;
import base.Task;

public class LoadContestProbListTask extends Task {
	private boolean msg;
	private List<Problem> list;
	private JspWriter out;
	private int cot_id;
	
	public  LoadContestProbListTask(JspWriter out){
		this.out=out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		cot_id=Integer.valueOf(request.getParameter("cot_id"));
		Contest contest=ContestORM.getInstance().findById(cot_id);
		
		if(contest.getStart_time().after(new Date(System.currentTimeMillis()))){
			msg=false;
		}else{
			msg=true;
		}
		
		list=ContestORM.getInstance().loadContestProblemList(cot_id);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		if(msg){
			for(int i=0;i<list.size();i++){
				Problem prb=list.get(i);
				out.println("<tr>");
				out.println("<td>"+(char)(i+'A')+"</td>");
				out.println("<td><a target=\"__blank\" href=\"./problem.jsp?prb_id="+prb.getPrb_id()+"&type=0&undetermined_id="
						+cot_id+"&prb_idx=-1\">"+prb.getTitle()+"</a></td>");
				out.println("</tr>");
			}		
		}else{
			response.sendRedirect("./Error.jsp?msg=contest has not started.");
		}
		
	}

}
