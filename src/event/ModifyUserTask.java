package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.User;
import DB.UserORM;
import base.Task;

public class ModifyUserTask extends Task {
	private int errno = 0;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		 String user_id = request.getParameter("user_id");
		 String user_name = request.getParameter("user_name");
		 int user_type = Integer.valueOf(request.getParameter("user_type"));
		 String user_psw = request.getParameter("user_psw");
		 
		 User u  = new User();
		 
		 u.setUser_id(user_id);
		 u.setUser_name(user_name);
		 u.setUser_psw(user_psw);
		 u.setUser_type(user_type);
		 
		 User old_u = UserORM.getInstance().loadById(user_id);
		 
		 if(old_u==null){
			 errno = 2;
			 return ;
		 }
		 
		 if(old_u.getUser_type()>0 && u.getUser_type()==0){
			 errno = 3;
			 return ;
		 }
		 
		 if(!UserORM.getInstance().updUser(u)){
			 errno = 1;
		 }
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		String msg = null;
		
		switch(errno){
			case 0: msg = new String("Success!"); break;
			case 1: msg = new String("更新信息时候发生错误，请查看错误日志"); break;
			case 2: msg = new String("用户不存在"); break;
			case 3: msg = new String("无法将权限降为新手"); break;
		}
		
		response.getWriter().print(msg);
	}

}
