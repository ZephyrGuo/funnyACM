package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONStringer;

import DB.User;
import DB.UserORM;
import DB.UserRecord;
import DB.UserRecordORM;
import base.Task;

public class QueryUserRecord extends Task {
	
	private int errno = 0;
	private User u;
	private UserRecord r;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String user_id = request.getParameter("user_id");		
		u = UserORM.getInstance().loadById(user_id);
	
		if(u==null){
			errno = 1;
			return ;
		}
		
		r = UserRecordORM.getInstance().loadById(user_id);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		String json_str = null;
		
		switch(errno){
		case 0: json_str = new JSONStringer().object()
					.key("errno").value("0")
					.key("user_name").value(u.getUser_name())
					.key("total_task").value(r.getTotal_task())
					.key("total_problem").value(r.getTotal_problem())
					.key("best_rank").value(r.getBest_rank())
					.key("best_score").value(r.getBest_score())
					.key("total_score").value(r.getTotal_score())
					.endObject()
					.toString();
			
				break;
		case 1: json_str = new JSONStringer().object()
					.key("errno").value("1")
					.endObject()
					.toString();
		
				break;
		}
		
		response.getWriter().print(json_str);
	}

}
