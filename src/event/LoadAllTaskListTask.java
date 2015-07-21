package event;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.Contest;
import DB.ContestORM;
import DB.TaskInfo;
import DB.TaskInfoORM;
import base.Task;

public class LoadAllTaskListTask extends Task {

	private List<TaskInfo> list;
	private JspWriter out;
	
	public LoadAllTaskListTask(JspWriter out){
		this.out = out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		int i = Integer.valueOf(request.getParameter("i"));
		int n = Integer.valueOf(request.getParameter("n"));
		
		
		list = TaskInfoORM.getInstance().loadTaskByPaging(i,n);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
				
		for(TaskInfo t: list){
			out.println("<tr>");
			out.println("<td><h5>"+t.getTask_name()+"</h5></td>");
			out.println("<td align=\"center\"><div class=\"btn-group\" role=\"group\" >");
			out.println("<a type=\"button\" class=\"btn btn-primary\" "+
					"href=\"./task_statistics.jsp?task_id="+t.getTask_id()+"\""+
					">统计</a>");
			out.println("<a class=\"btn btn-success\" href=\"./task_edit.jsp?task_id="+t.getTask_id()+"\">编辑</a>");
			out.println("</td></div></tr>");		
		}

	}

}
