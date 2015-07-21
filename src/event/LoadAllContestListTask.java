package event;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.Contest;
import DB.ContestORM;
import base.Task;

public class LoadAllContestListTask extends Task {

	private List<Contest> clist;
	private JspWriter out;
	
	public LoadAllContestListTask(JspWriter out){
		this.out = out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		
		int i = Integer.valueOf(request.getParameter("i"));
		int n = Integer.valueOf(request.getParameter("n"));
		
		clist = ContestORM.getInstance().loadByPaging(i,n);		
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
				
		for(Contest c: clist){
			out.println("<tr>");
			out.println("<td><h5>"+c.getCot_name()+"</h5></td>");
			out.println("<td align=\"center\"><div class=\"btn-group\" role=\"group\" >");
			out.println("<button type=\"button\" class=\"btn btn-primary\" onclick=calRating("+c.getCot_id()+") >计分</button>");
			out.println("<a class=\"btn btn-success\" href=\"./contest_edit.jsp?cot_id="+c.getCot_id()+"\">编辑</a>");
			out.println("<a class=\"btn btn-info\" target=\"_blank\" href=\"../contest_rank.jsp?cot_id="+c.getCot_id()+"\">榜单</a>");
			out.println("</td></div></tr>");		
		}

	}

}
