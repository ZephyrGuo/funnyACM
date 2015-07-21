package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Problem;
import DB.ProblemORM;
import DB.TaskTemplate;
import DB.TaskTemplateORM;
import base.Task;

public class EditTaskTplTask extends Task {
	
	private int errno = 0;
	private String not_exist_prb;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String task_tpl_name = request.getParameter("task_tpl_name");
		String prb_list = request.getParameter("prb_list"); 
		int task_tpl_id = Integer.valueOf(request.getParameter("task_tpl_id"));
		
		String[] strs = prb_list.split(";");
		prb_list = "";
		
		for(int i=0;i<strs.length;i++){
			if(strs[i].matches("[A-Z]+\\.\\d+")){
				String[] t = strs[i].split("\\.");
				System.out.println(strs[i]+" "+t.length);
				Problem p = ProblemORM.getInstance().LoadInfoByOJ(t[0], t[1]);
				if(p==null){
					not_exist_prb = strs[i];
					errno = 3;
					return ;
				}
				if(i!=0) prb_list += ";";
				prb_list += p.getPrb_id();
			}else{
				errno = 1;
				return ;
			}
		}
		
		TaskTemplate t = new TaskTemplate();
		
		t.setTask_tpl_id(task_tpl_id);
		t.setTask_tpl_name(task_tpl_name);
		t.setPrb_list(prb_list);
		
		if(!TaskTemplateORM.getInstance().update(t)){
			errno = 2;
		}
		
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		String msg = null;
		
		switch(errno){
			case 0: msg = new String("Success!"); break;
			case 1: msg = new String("题目格式错误"); break;
			case 2: msg = new String("更新题目模版发生错误，请查看错误日志"); break;
			case 3: msg = new String(not_exist_prb+"不在题库中"); break;
		}
		
		response.getWriter().print(msg);
	}

}
