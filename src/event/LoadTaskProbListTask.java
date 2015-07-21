package event;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.Problem;
import DB.TaskInfo;
import DB.TaskInfoORM;
import DB.TaskTemplateORM;
import base.Task;

public class LoadTaskProbListTask extends Task {

	private List<Problem> list;
	private JspWriter out;
	private int task_id;
	
	public  LoadTaskProbListTask(JspWriter out){
		this.out=out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		task_id = Integer.valueOf(request.getParameter("task_id"));
		TaskInfo task = TaskInfoORM.getInstance().loadById(task_id);
		
		list=TaskTemplateORM.getInstance().loadProblemList(task.getTask_tpl_id());
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		
		for(int i=0;i<list.size();i++){
			Problem prb=list.get(i);
			out.println("<tr>");
			out.println("<td>"+(char)(i+'A')+"</td>");
			out.println("<td><a target=\"__blank\" href=\"./problem.jsp?prb_id="+prb.getPrb_id()+"&type=1&undetermined_id="+task_id
					+"&prb_idx="+i+"\">"+prb.getTitle()+"</a></td>");
			out.println("</tr>");
		}		
			
	}

}
