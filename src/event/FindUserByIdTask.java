package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONStringer;

import DB.User;
import DB.UserORM;
import base.Task;

public class FindUserByIdTask extends Task {
	private User u;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String user_id = request.getParameter("user_id");	
		u = UserORM.getInstance().loadById(user_id);	
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		String res_json;
		
		if(u==null){
			res_json = new JSONStringer().object()
					.key("errno")
					.value("1")
					.endObject()
					.toString();
		}else{
			res_json = new JSONStringer().object()
					.key("errno")
					.value("0")
					.key("name")
					.value(u.getUser_name())
					.key("type")
					.value(u.getUser_type())
					.endObject()
					.toString();			
		}
		
		response.getWriter().print(res_json);
	}

}
