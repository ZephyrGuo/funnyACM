package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Contest;
import DB.ContestORM;
import base.Task;

public class LoadContestInfoTask extends Task {
private Contest contest;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		int cot_id = Integer.parseInt(request.getParameter("cot_id"));
		contest = ContestORM.getInstance().findById(cot_id);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

	}

	public Contest getContest(){
		return contest;
	}

}
