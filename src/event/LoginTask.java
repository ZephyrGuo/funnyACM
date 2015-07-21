package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.User;
import DB.UserORM;
import base.Task;

public class LoginTask extends Task {
	private boolean Login_status;
	private User u;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException{
		u=new User();
		u.setUser_id(request.getParameter("user_id"));
		u.setUser_psw(request.getParameter("user_psw"));
				
		if(this.Login_status=UserORM.getInstance().check(u)){
			u=UserORM.getInstance().loadById(u.getUser_id());
			request.getSession().setAttribute("handle", u.getUser_id());
			request.getSession().setAttribute("type", u.getUser_type());
		}
		
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException{

		if(Login_status){
			String url;
			
			if(u.getUser_type()==0){
				url="./newbie.jsp?sectionN=0";
			}else if(u.getUser_type()==1){
				url="./train.jsp?i=0&n=15";
			}else if(u.getUser_type()==2){
				url="./admin.jsp";
			}else{
				url="./index.jsp";
			}
			
			response.sendRedirect(url);
		}else{
			response.sendRedirect("./Error.jsp?msg=Account or password error, login failed.");
		}
	}

}
