package event;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Season;
import DB.SeasonORM;
import base.Task;

public class EditSeasonTask extends Task {
	
	private int errno = 0;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		Season s=new Season();

		s.setSea_des(request.getParameter("sea_des"));
		s.setSea_len(Integer.parseInt(request.getParameter("sea_len")));
		s.setSea_name(request.getParameter("sea_name"));
		s.setSea_id(Integer.valueOf(request.getParameter("sea_id")));
		
		if(!SeasonORM.getInstance().update(s)){
			errno = 1;
		}

	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		String msg = null;
		
		switch(errno){
			case 0: msg = new String("Success!"); break; 
			case 1: msg = new String("更新赛季信息时发生错误，请查看错误日志"); break;
		}
		
		response.getWriter().print(msg);
	}

}
