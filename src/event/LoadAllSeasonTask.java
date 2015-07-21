package event;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.Season;
import DB.SeasonORM;
import DB.TaskTemplate;
import DB.TaskTemplateORM;
import base.Task;

public class LoadAllSeasonTask extends Task {
	
	private JspWriter out;
	private List<Season> list;
	
	public LoadAllSeasonTask(JspWriter out){
		this.out = out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		list = SeasonORM.getInstance().loadAllSeason();

	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
		
		for(Season s: list){
			out.println("<tr>");
			
			Date t = new Date(s.getSea_create().getTime());
			
			out.println("<td><h5>"+s.getSea_name()+"&nbsp;<small>创建日期:"+fmt.format(t)
					+" 持续："+s.getSea_len()+"天</small></h5>"+
					"<p>"+s.getSea_des()+"</p></td>");
			
			out.println("<td align=\"center\"><div class=\"btn-group\" role=\"group\" >");
			out.println("<a type=\"button\" class=\"btn btn-primary\" href=\"../season_board.jsp?sea_id="
					+s.getSea_id()+"\" target=\"_blank\">榜单</a>");
			out.println("<a class=\"btn btn-success\" href=\"./season_edit.jsp?sea_id="+s.getSea_id()+"\">编辑</a>");
			out.println("</td></div></tr>");
		}
	}

}
