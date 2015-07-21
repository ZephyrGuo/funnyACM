package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Problem;
import DB.ProblemORM;
import DB.TaskTemplate;
import DB.TaskTemplateORM;
import base.Task;

public class CreateTaskTplTask extends Task {

	private int errno = 0;
	private String not_exist_prb;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		 String task_tpl_name = request.getParameter("task_tpl_name");
		 String[] prb_set = request.getParameter("prb_set").split(";");
		 String[] oj_set = request.getParameter("oj_set").split(";"); 

		 TaskTemplate tpl = new TaskTemplate();
		 String prb_list = "";
		 
		 tpl.setTask_tpl_name(task_tpl_name);
		 	
		 if(prb_set.length != oj_set.length){
			 errno = 1;
			 return ;
		 }
		 
		 for(int i=0;i<prb_set.length;i++){
			 Problem p = ProblemORM.getInstance().LoadInfoByOJ(oj_set[i],prb_set[i]);
			 if(p == null){
				 errno = 2;
				 not_exist_prb = oj_set[i]+"."+prb_set[i];
				 return ;
			 }
			 if(i!=0) prb_list += ";";
			 prb_list += p.getPrb_id();
		 }
		 
		 tpl.setPrb_list(prb_list);
		 
		 if(!TaskTemplateORM.getInstance().save(tpl)){
			 errno = 3;
		 }
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		String msg = null;
		
		switch(errno){
		case 0: msg = new String("Success!"); break;
		case 1: msg = new String("OJ选项数目与题目数目不同，请查看错误日志"); break;
		case 2: msg = new String(not_exist_prb+"不在题库中，请先添加"); break;
		case 3: msg = new String("存储模板时发生错误，请查看错误日志"); break;
		}
		
		response.getWriter().print(msg);
	}

}
