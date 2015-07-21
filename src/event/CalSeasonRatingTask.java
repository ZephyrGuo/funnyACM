package event;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Board;
import DB.BoardORM;
import DB.Contest;
import DB.ContestORM;
import DB.SeasonORM;
import base.Task;

public class CalSeasonRatingTask extends Task {

	int status;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		int cot_id = Integer.parseInt(request.getParameter("cot_id"));
		Contest cot = ContestORM.getInstance().findById(cot_id);
		
		if(!cot.getEnd_time().before(new Date(System.currentTimeMillis()))){
			status = 1;
			return ;
		}
		
		Board board = BoardORM.getInstance().loadByContestId(cot_id);
		List<String>[] table = board.getBoard();
		int N = table.length;  //总人数
		int M = 0; 			   //出题种数
		int last_ac_cnt = -1;
		
		for(List<String> l : table){
			int cur_ac_cnt = Integer.parseInt(l.get(2));
			if(cur_ac_cnt != last_ac_cnt) M++;
			last_ac_cnt = cur_ac_cnt;
		}
		
		String[] tmp = cot.getSeason_list().split(";");
		int[] sea_id_list = new int[tmp.length];
		
		for(int i=0;i<tmp.length;i++){
			try{
				sea_id_list[i] = Integer.parseInt(tmp[i]);
			}catch(NumberFormatException e){
				e.printStackTrace();
				status = 2;
				return ;
			}
		}
		
		last_ac_cnt = -1;
	
		for(int i=0,j=0;i<table.length;i++){
			List<String> l = table[i];
			int cur_ac_cnt = Integer.parseInt(l.get(2));
			if(last_ac_cnt != cur_ac_cnt) j++; //j是题数排名
			last_ac_cnt = cur_ac_cnt;
			
			int r = Integer.parseInt(l.get(0)); //排名
			int add_score = 50*(M-j) + (int)Math.ceil((N-r)*(N*0.09618)); //得分
			
			System.out.println("M: "+M+" j:"+j+" N:"+N+" r:"+r);
			
			for(int sea_id : sea_id_list){
				if(!SeasonORM.getInstance().calRating(sea_id, board.getUserId(i), add_score)){
					System.out.println("update rating failed[user_id:"+board.getUserId(i)+",sea_id:"+sea_id+",add_score:"+add_score+"]");
					status = 3;
				}
			}
			
		}
		
		status = 0;
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {	
		String res = null;
		
		switch(status){
			case 0: res = new String("Success!"); break;
			case 1: res = new String("Not the end of the contest"); break;
			case 2: res = new String("System error, please contact the developer"); break;
			case 3: res = new String("一些队员rating更新失败，请查看错误日志");
		}
		
		response.getWriter().println(res);
	}

}
