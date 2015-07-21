package event;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.Season;
import DB.SeasonORM;
import base.Task;

public class LoadApplySeasonTask extends Task {
	List<Season> canApply;
	List<Season> joined;
	private JspWriter out;

	
	public LoadApplySeasonTask(JspWriter out){
		this.out=out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String user_id=(String)request.getSession().getAttribute("handle");
		canApply=SeasonORM.getInstance().loadCanApplySeason(user_id);
		joined=SeasonORM.getInstance().loadJoinedSeason(user_id);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		out.println("<table><tr>");
		
		// 已通过申请的赛季 list
		out.println("<td width=\"50%\" valign=\"top\">");
		out.println("<div class=\"panel panel-default\" width=\"100%\">");
		out.println("<div class=\"panel-heading\"><h4>已通过申请的赛季</h4></div>");
		out.println("<div class=\"panel-body\">");
		out.println("<div class=\"list-group\">");
		output(joined,1);
		out.println("</div></div></div></td>");
		
		// 可申请的赛季 list
		out.println("<td width=\"50%\" valign=\"top\">");
		out.println("<div class=\"panel panel-default\" width=\"100%\">");
		out.println("<div class=\"panel-heading\"><h4>可申请的赛季</h4></div>");
		out.println("<div class=\"panel-body\">");
		out.println("<div class=\"list-group\">");
		output(canApply,0);
		out.println("</div></div></div></td>");
		
		out.println("</tr></table>");
	}
	
	private void output(List<Season> list,int type) throws IOException{
		for(int i=0;i<list.size();i++){
			Season sea=list.get(i);
			out.println(this.getTagByType(type, sea));
		    out.println("<h4 class=\"list-group-item-heading\">"+sea.getSea_name()+"</h4>");
		    DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
		    out.println("<p class=\"list-group-item-text\">创建时间："+fmt.format(sea.getSea_create())+"</p>");
		    out.println("<p class=\"list-group-item-text\">持续："+sea.getSea_len()+"天</p>");
		    out.println("<p class=\"list-group-item-text\">"+sea.getSea_des()+"</p>");
		    out.println("</a>");
		}
	}
	
	private String getTagByType(int type,Season sea){

		if(type==0){
			String canApplyTag="<a class=\"list-group-item brand-success\" onclick=\"apply(this,"+sea.getSea_id()+")\">";
			return canApplyTag;
		}
		
		String joinedTag="<a href=\"./season_board.jsp?sea_id="+sea.getSea_id()+"\" target=\"_blank\" class=\"list-group-item active\">";
		
		return joinedTag;
	}
	
}
