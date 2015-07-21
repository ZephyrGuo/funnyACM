package event;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.TaskInfo;
import DB.TaskInfoORM;
import DB.UserDoTaskORM;
import base.Task;

public class LoadTaskStatusTask extends Task {
	
	private List<Map.Entry<String,Integer>> list;
	private TaskInfo task;
	private JspWriter out;
	
	
	public LoadTaskStatusTask(JspWriter out){
		this.out = out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		int task_id = Integer.valueOf(request.getParameter("task_id")); 

		list = UserDoTaskORM.getInstance().loadStatusByTaskId(task_id);	
		task = TaskInfoORM.getInstance().loadById(task_id);		
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {		
		for(Map.Entry<String,Integer> pair : list){
			out.println(getHTML(pair.getKey(),pair.getValue()));
		}
	}
	
	private String getHTML(String user_name,int status){
		String HTML = "<tr>";	
		HTML += "<td>"+user_name+"</td>";
		
		if(task.isComplete(status)){
			HTML += "<td><span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span></td>";
		}else{
			HTML += "<td><span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></td>";
		}
		
		HTML += "<td>"+(status>>26)+"</td></tr>";
		
		return HTML;
	}
}
