package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Season;
import DB.SeasonORM;
import base.Task;

public class LoadSeasonTask extends Task {
	
	private Season s;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		int sea_id=Integer.valueOf(request.getParameter("sea_id"));
		s=SeasonORM.getInstance().findbyid(sea_id);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		

	}
	
	public Season getSeason(){
		return s;
	}

}
