package event;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.ApplySeason;
import DB.SeasonORM;
import base.Task;

public class LoadApplyRecordTask extends Task {

	private JspWriter out;
	private List<ApplySeason> list;
	
	
	public LoadApplyRecordTask(JspWriter out){
		this.out = out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		list = SeasonORM.getInstance().loadAllApply();
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		
		for(ApplySeason s : list){
			out.println("<tr><td>"+s.getUser_name()+"&nbsp;申请&nbsp;"+s.getSea_name()+"</td>");
			out.println("<td align=\"center\"><div class=\"btn-group\" role=\"group\" >");
			
			String pass = "approveSeason("+s.getSea_id()+",'"+s.getUser_id()+"',1,this)";
			String reject = "approveSeason("+s.getSea_id()+",'"+s.getUser_id()+"',0,this)";
			
			out.println("<button type=\"button\" class=\"btn btn-primary\" onclick=\""+pass+"\">同意</button>");
			out.println("<button type=\"button\" class=\"btn btn-danger\" onclick=\""+reject+"\">拒绝</button>");
		
			out.println("</td></div></tr>");	
		}
	}

}
