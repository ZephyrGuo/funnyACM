package event;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.User;
import DB.UserORM;
import base.Task;

public class FindUserTask extends Task {

	String HTML = "";
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String user_name = request.getParameter("user_name");
		
		//System.out.println("FindUserTask{user_name:"+user_name+"}");
		
		List<User> list = UserORM.getInstance().findByName(user_name);
		
		for(User u : list){
			if(u.getUser_type() == 1){
				HTML += getHTML(u);
			}
		}
		
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		response.getWriter().print(HTML);
	}

	private String getHTML(User u){
		String HTML = "";
		
		HTML += "<li class=\"list-group-item\"><label class=\"checkbox-inline\">";
		HTML += "<input type=\"checkbox\" value=\""+u.getUser_name() + "(" + u.getUser_id() + ")"+"\">";
		HTML += u.getUser_name() + "(" + u.getUser_id() + ")"+"</label></li>";
		
		return HTML;
	}
	
}
