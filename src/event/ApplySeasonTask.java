package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.SeasonORM;
import base.Task;

public class ApplySeasonTask extends Task {

	int errno = 0;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String user_id=(String)request.getSession().getAttribute("handle");
		
		if(user_id==null){
			errno = 1;
			return ;
		}
		
		int sea_id=Integer.parseInt(request.getParameter("sea_id"));
		SeasonORM.getInstance().insert_season_apply(sea_id, user_id);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		switch(errno){
			case 1: response.sendRedirect("./index.jsp");
		}
	}

}
