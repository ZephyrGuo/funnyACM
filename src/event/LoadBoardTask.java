package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Board;
import DB.BoardORM;
import DB.ContestORM;
import base.Task;

public class LoadBoardTask extends Task {

	private Board board;
	private int errno = 0;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		int cot_id;
		
		try{ 
			cot_id = Integer.parseInt(request.getParameter("cot_id"));
		}catch(Exception e){ 
			errno = 1;
			return ;
		}
		if(ContestORM.getInstance().findById(cot_id) == null){
			errno = 2;
			return ;
		}
		board = BoardORM.getInstance().loadByContestId(cot_id);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {

	}
	
	public int getErrno(){
		return this.errno;
	}
	
	public Board getBoard(){
		return board;
	}
}
