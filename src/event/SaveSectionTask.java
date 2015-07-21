package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Problem;
import DB.ProblemORM;
import DB.TaskTemplate;
import DB.TaskTemplateORM;
import base.Task;

public class SaveSectionTask extends Task {

	private int errno = 0;
	private String not_exist_prb;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String oj_set[] = request.getParameter("oj_list").split(";");
		String prb_set[] = request.getParameter("oj_id_list").split(";");
			
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
			 prb_set[i] = String.valueOf(p.getPrb_id());
		 }
		 
		 TaskTemplate tpl = TaskTemplateORM.getInstance().loadById(0);
		 String prb_list = "";
		 
		 for(int i=0;i<4;i++){
			 if(i!=0) prb_list+=";";
			 prb_list += prb_set[i];
			 tpl.setPrb_list(prb_list);
			 if(!TaskTemplateORM.getInstance().update(tpl)){
				 errno = 3;
				 return ;
			 }
		 }
		 
		 tpl = TaskTemplateORM.getInstance().loadById(1);
		 prb_list = "";
		 
		 for(int i=4;i<8;i++){
			 if(i!=4) prb_list+=";";
			 prb_list += prb_set[i];
			 tpl.setPrb_list(prb_list);
			 if(!TaskTemplateORM.getInstance().update(tpl)){
				 errno = 3;
				 return ;
			 }
		 }
		 
		 tpl = TaskTemplateORM.getInstance().loadById(2);
		 prb_list = "";
		 
		 for(int i=8;i<12;i++){
			 if(i!=8) prb_list+=";";
			 prb_list += prb_set[i];
			 tpl.setPrb_list(prb_list);
			 if(!TaskTemplateORM.getInstance().update(tpl)){
				 errno = 3;
				 return ;
			 }
		 }
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		String msg = null;
		
		switch(errno){
			case 0: msg = new String("Success!"); break; 
			case 1: msg = new String("OJ选项数目与题目数目不同，请查看错误日志"); break;
			case 2: msg = new String(not_exist_prb+"不存在题库"); break;
			case 3: msg = new String("更新闯关信息时发生错误，请查看错误日志"); break;
		}
		
		response.getWriter().print(msg);
	}

}
