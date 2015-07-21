package event;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Season;
import DB.SeasonORM;
import DB.User;
import base.Task;

public class FindSeasonTask extends Task {

	private String HTML = "";
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String season_name = request.getParameter("sea_name");
		List<Season> list = SeasonORM.getInstance().findbyName(season_name);

		for(Season s: list){
			HTML += this.getHTML(s);
		}
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {

		response.getWriter().print(HTML);
		
	}

	private String getHTML(Season s){
		String HTML = "";		
		long time = s.getSea_create().getTime() + s.getSea_len()*24*60*60*1000L;
		Date end_time = new Date(time);
		Date now_time = new Date(System.currentTimeMillis());
		
		if(end_time.before(now_time)) return "";
			
		HTML += "<li class=\"list-group-item\"><label class=\"checkbox-inline\">";
		HTML += "<input type=\"checkbox\" value=\"["+ s.getSea_id() + "]"+s.getSea_name()+"\">";
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyy-MM-dd");
		
		HTML += "["+s.getSea_id()+"]&nbsp;" + s.getSea_name() + "&nbsp;<small>结束日期:" + fmt.format(end_time) + "</small></label>";
		HTML += "<p>" + s.getSea_des() + "</p></li>";

		return HTML;
	}
}
