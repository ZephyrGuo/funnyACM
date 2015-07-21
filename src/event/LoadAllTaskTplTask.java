package event;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.TaskInfo;
import DB.TaskInfoORM;
import DB.TaskTemplate;
import DB.TaskTemplateORM;
import base.Task;

public class LoadAllTaskTplTask extends Task {

	private List<TaskTemplate> list;
	private JspWriter out;
	
	public LoadAllTaskTplTask(JspWriter out){
		this.out = out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		int i = Integer.valueOf(request.getParameter("i"));
		int n = Integer.valueOf(request.getParameter("n"));
		
		list = TaskTemplateORM.getInstance().loadByPaging(i, n);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
				
		for(TaskTemplate t: list){
			out.println("<tr>");
			out.println("<td><h5>"+t.getTask_tpl_name()+"</h5></td>");
			out.println("<td align=\"center\"><div class=\"btn-group\" role=\"group\" >");
			out.println("<a class=\"btn btn-primary\" href=\"./task_tpl_detail.jsp?task_tpl_id="+t.getTask_tpl_id()+"\">详细</a>");	
			out.println("<a class=\"btn btn-success\" href=\"./task_tpl_edit.jsp?task_tpl_id="+t.getTask_tpl_id()+"\">编辑</a>");
			out.println("</div></td></tr>");		
		}

	}

}
