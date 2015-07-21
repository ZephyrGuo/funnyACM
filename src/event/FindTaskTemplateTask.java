package event;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Problem;
import DB.ProblemORM;
import DB.TaskTemplate;
import DB.TaskTemplateORM;
import base.Task;

public class FindTaskTemplateTask extends Task {
	
	String HTML = "";
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String tpl_name = request.getParameter("tpl_name");
		List<TaskTemplate> list = TaskTemplateORM.getInstance().findByName(tpl_name);
		
		for(TaskTemplate t : list){
			String[] prb_id_list = t.getPrb_list().split(";");
			String prb_set = "{";
			for(int i=0;i<prb_id_list.length;i++){
				Problem p = ProblemORM.getInstance().LoadInfoByPrbId(Integer.parseInt(prb_id_list[i]));
				if(i!=0) prb_set += ", ";
				prb_set += p.getOj_name() + "." + p.getOj_id();				
			}
			prb_set += "}";
			
			HTML += getHTML(t,prb_set);			
		}
		
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		response.getWriter().print(HTML);
	}

	private String getHTML(TaskTemplate t, String prb_set){
		String HTML = "";
		HTML += "<a href=\"#\" class=\"list-group-item\" ondblclick=\"chooseTaskTemplate("+t.getTask_tpl_id()+",this)\" >";
		HTML += "<h4 class=\"list-group-item-heading\">" + t.getTask_tpl_name() + "</h4>";
		HTML += "<p class=\"list-group-item-text\">题目: " + prb_set + "</p></a>"; 	
		return HTML;
	}
	
}
