package event;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Contest;
import DB.ContestORM;
import DB.Problem;
import DB.ProblemORM;
import base.Task;

public class EditContestTask extends Task {
	
	private int errno = 0;
	private String errmsg;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		
		int cot_id = Integer.valueOf(request.getParameter("cot_id"));
		Contest c = ContestORM.getInstance().findById(cot_id);
		
		if(c.getStart_time().before(new Date(System.currentTimeMillis()))){
			errno = 5;
			return ;
		}
		
		String cot_name = request.getParameter("cot_name");		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Timestamp start_time = null;
		Timestamp end_time = null;
		
		try {
			 start_time = new Timestamp(fmt.parse(request.getParameter("start_time")).getTime());	
			 end_time = new Timestamp(fmt.parse(request.getParameter("end_time")).getTime());
		} catch (ParseException e) {
			errno = 1;
			e.printStackTrace();
			return ;
		}
		
		String season_list = request.getParameter("sea_id_list");
		String prb_list = "";
		String[] strs = request.getParameter("prb_list").split(";");
		
		
		for(int i=0;i<strs.length;i++){
			 if(strs[i].equals("")) continue;
			 
			 if(!strs[i].matches("[A-Z]+\\.\\d+")){
				 errno = 2;
				 errmsg = strs[i];
				 return ;
			 }
			 
			 String t[] = strs[i].split("\\.");
			 Problem p = ProblemORM.getInstance().LoadInfoByOJ(t[0],t[1]);
			 
			 if(p == null){
				 errno = 3;
				 errmsg = strs[i];
				 return ;
			 }
			 
			 if(i!=0) prb_list += ";";
			 prb_list += p.getPrb_id();
		 }
		 
		 c = new Contest();		 
		 c.setCot_name(cot_name);
		 c.setCot_id(cot_id);
		 c.setStart_time(start_time);
		 c.setEnd_time(end_time);
		 c.setPrb_list(prb_list);
		 c.setSeason_list(season_list);
		 
		 if(!ContestORM.getInstance().update(c)){
			 errno = 4;
		 }
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		String msg = null;
		
		switch(errno){
		case 0: msg = new String("Success!"); break;
		case 1: msg = new String("时间格式错误"); break;
		case 2: msg = new String("题目格式错误："+errmsg); break;
		case 3: msg = new String(errmsg+"不存在题库"); break;
		case 4: msg = new String("更新比赛信息时发生错误，请查看错误日志"); break;
		case 5: msg = new String("比赛正在进行中或已结束，无法做任何修改"); break;
		}
		
		response.getWriter().print(msg);
	}

}
