package event;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.TaskInfo;
import DB.TaskInfoORM;
import base.Task;

public class EditTaskInfoTask extends Task {
	
	private int errno = 0;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		
		 String task_name = request.getParameter("task_name");
		 String task_description = request.getParameter("task_description");
		 int task_id = Integer.valueOf(request.getParameter("task_id"));
		 
		 SimpleDateFormat fmt =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 long time_offset = 0;
		 
		 try {
			 time_offset = fmt.parse(request.getParameter("start_time")).getTime();
		 } catch (ParseException e) {			
			e.printStackTrace();
			errno = 1;
			return ;
		 }
		 
		 Timestamp start_time = new Timestamp(time_offset);
		 
		 int task_tpl_id = Integer.valueOf(request.getParameter("task_tpl_id"));
		 String[] user_list = request.getParameter("user_list").split(";");
		 int condition_mask = Integer.valueOf(request.getParameter("condition_mask"));
		 
		 
		 for(String user_id : user_list){
			 
			 if(user_id.equals("")) continue;
			 
			 if(TaskInfoORM.getInstance().ishasTask(user_id, task_id)) continue;
			 
			 if(!TaskInfoORM.getInstance().insertDoTaskStatus(task_id, user_id, 0)){
				 errno = 2;
				 return ;
			 }
		 }
		 		 
		 TaskInfo task = TaskInfoORM.getInstance().loadById(task_id);
		 
		 if(task.getTask_tpl_id()!=task_tpl_id){
			 if(!TaskInfoORM.getInstance().clear(task_id)){
				 errno = 3;
				 return ;
			 }
		 }
		 
		 task.setTask_name(task_name);
		 task.setTask_description(task_description);
		 task.setStart_time(start_time);
		 task.setCondition_mask(condition_mask);
		 task.setTask_tpl_id(task_tpl_id);
		 
		 if(!TaskInfoORM.getInstance().update(task)){
			 errno = 4;
			 return ;
		 }
		 
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		String msg = null;
		
		switch(errno){
			case 0: msg = new String("Success!"); break;
			case 1: msg = new String("时间格式错误"); break;
			case 2: msg = new String("存储队员任务完成状态时发生错误"); break;
			case 3: msg = new String("重置任务完成状态发生错误"); break;
			case 4: msg = new String("更新任务信息发生错误"); break;
		}
		
		response.getWriter().print(msg);
	}

}
