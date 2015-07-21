package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Board;
import DB.SeasonBoard;
import DB.SeasonBoardORM;
import base.Task;

public class LoadSeasonBoardTask extends Task{
	
	private SeasonBoard board;
		
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		int sea_id=Integer.valueOf(request.getParameter("sea_id"));	
		board=SeasonBoardORM.getInstance().loadById(sea_id);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public SeasonBoard getSeasonBoard(){
		return board;
	}
}
