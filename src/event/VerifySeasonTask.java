package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.SeasonORM;
import base.Task;

public class VerifySeasonTask extends Task{

	private int errno = 0;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		/* operator:
		*  0 拒绝
		*  1通过
		*/ 	
		String user_id=request.getParameter("user_id");
		int sea_id=Integer.valueOf(request.getParameter("sea_id"));
		int operator=Integer.valueOf(request.getParameter("operator"));
		
		if(!SeasonORM.getInstance().dealApply(user_id, sea_id, operator)){
			errno = 1;
		}
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		String msg = null;
		
		switch(errno){
			case 0: msg = new String("success"); break;
			case 1: msg = new String("处理发生错误，请查看错误日志"); break;
		}
		
		response.getWriter().println(msg);
	}

}