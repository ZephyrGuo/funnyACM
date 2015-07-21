package event;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Contest;
import DB.ContestORM;
import DB.Problem;
import DB.ProblemORM;
import base.Task;

public class CreateContestTask extends Task {

	private int errno = 0;
	private String not_exist_prb;
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
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
		String[] oj_set = request.getParameter("oj_set").split(";");
		String[] prb_set = request.getParameter("prb_set").split(";");
		String prb_list = "";
		
		 if(prb_set.length != oj_set.length){
			 errno = 2;
			 return ;
		 }
		 
		 for(int i=0;i<prb_set.length;i++){
			 Problem p = ProblemORM.getInstance().LoadInfoByOJ(oj_set[i],prb_set[i]);
			 if(p == null){
				 errno = 3;
				 not_exist_prb = oj_set[i]+"."+prb_set[i];
				 return ;
			 }
			 if(i!=0) prb_list += ";";
			 prb_list += p.getPrb_id();
		 }
		 
		 if(start_time.after(end_time)){
			 errno = 4;
			 return ;
		 }
		 
		 if(season_list.equals("")){
			 errno = 5;
			 return ;
		 }
		 
		 Contest c = new Contest();;
		 
		 c.setCot_name(cot_name);
		 c.setStart_time(start_time);
		 c.setEnd_time(end_time);
		 c.setPrb_list(prb_list);
		 c.setSeason_list(season_list);
		 
		 if(!ContestORM.getInstance().save(c)){
			 errno = 6;
		 }
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		String msg = null;
		
		switch(errno){
		case 0: msg = new String("Success!"); break;
		case 1: msg = new String("时间格式错误"); break;
		case 2: msg = new String("OJ选项数目与题目数目不同，请查看错误日志"); break;
		case 3: msg = new String(not_exist_prb+"不在题库中，请先添加"); break;
		case 4: msg = new String("开始时间要比结束时间早"); break;
		case 5: msg = new String("至少需要选择一场赛季"); break;
		case 6: msg = new String("存储比赛信息时发生错误，请查看错误日志"); break;				
		}
		
		response.getWriter().print(msg);

	}

}
