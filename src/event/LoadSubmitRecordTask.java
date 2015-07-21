package event;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.SubmitRecord;
import DB.SubmitRecordORM;
import base.OnlineJudge;
import base.Task;

public class LoadSubmitRecordTask extends Task {
	List<SubmitRecord> list;
	private int errno = 0;
	private JspWriter out;
	
	public LoadSubmitRecordTask(JspWriter out){
		this.out = out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String user_id=(String)request.getSession().getAttribute("handle");
		
		int prb_id = -1;
		int type = -1;
		int undetermined_id = -1;
		
		try{
			prb_id=Integer.valueOf(request.getParameter("prb_id"));
			type=Integer.valueOf(request.getParameter("type"));		
			
			if(type==-1) return ;
			
			undetermined_id = Integer.parseInt(request.getParameter("undetermined_id"));
		}catch(Exception e){
			errno = 1;
			return ;
		}
		
		switch(type){
			case 0:			
				list=SubmitRecordORM.getInstance().loadContestHistory(user_id, prb_id, undetermined_id);
				break;
			case 1:
				list=SubmitRecordORM.getInstance().loadTaskHistory(user_id, prb_id, undetermined_id);
				break;
			case 2:
				list=SubmitRecordORM.getInstance().loadTaskHistory(user_id, prb_id, 0);
				break;
			default:
				errno = 1;
		}
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		
		if(list==null) return ;
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(int i=0;i<list.size();i++){
			SubmitRecord sr=list.get(i);
			
			out.println("<li class=\"list-group-item\">");
			
			String result_code = null;
			
			if(sr.getResult().equals(OnlineJudge.AC)){
				result_code = "<font color=\"#d9534f\">"+sr.getResult()+"</font>";
			}else if(sr.getResult().equals(OnlineJudge.CE)){
				result_code = "<a target=\"_blank\" href=\"./compile_page.jsp?smt_id="+sr.getSmt_id()+"\">"+sr.getResult()+"</a>";
			}else{
				result_code = "<font color=\"#5cb85c\">"+sr.getResult()+"</font>";
			}
		
			out.println(result_code+"&nbsp;&nbsp"+fmt.format(sr.getSmt_time())+"<br/>");
			out.println("耗时:"+sr.getRunTime()+"&nbsp;&nbsp;内存:"+sr.getRunMemory());
			out.println("<a target=\"_blank\" href=\"./code_page.jsp?smt_id="+sr.getSmt_id()+"\">code</a></li>");
			
		}
		
	}
		
	public int getErrno(){
		return errno;
	}
}
