package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.TaskInfoORM;
import DB.User;
import DB.UserDoTaskORM;
import DB.UserORM;
import base.Task;

public class RegistTask extends Task{
	private String res_msg;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		User u=new User();
		u.setUser_id(request.getParameter("user_id"));
		u.setUser_name(request.getParameter("user_name"));
		u.setUser_psw(request.getParameter("user_psw"));
		u.setUser_type(0);
		
		if(u.getUser_name().length()>5 ||
				u.getUser_name().length()<2){
			res_msg = "姓名字数不正确，应在[3,5]之间";
			return ;
		}
		
		if(request.getParameter("user_psw").length()>30 ||
				request.getParameter("user_psw").length()<6){
			res_msg = "密码长度不正确，应在[6,30]之间";
			return ;
		}
		
		if(u.getUser_id().length()>12 || 
				u.getUser_id().length()<6){
			res_msg = "帐号长度不正确，应在[6,12]之间";
			return ;
		}
		
		if(UserORM.getInstance().loadById(u.getUser_id())!=null){
			res_msg="该学号已被注册";
			return ;
		}
		
		if(!UserORM.getInstance().save(u)){
			res_msg="保存用户信息时发生错误，请联系管理员";
			return ;
		}
		
	 	if(!TaskInfoORM.getInstance().insertDoTaskStatus(0, u.getUser_id(), 0)){
	 		res_msg = "保存用户任务状态时发生错误，请联系管理员";
	 		return ;
	 	}
		
		request.getSession().setAttribute("handle", u.getUser_id());
		request.getSession().setAttribute("type", u.getUser_type());
	 	
		res_msg="success";
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		response.getWriter().print(res_msg);
	}

}