package event;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.Contest;
import DB.ContestORM;
import DB.Season;
import DB.SeasonORM;
import base.Task;

public class LoadCanJoinContestTask extends Task {

	private List<Contest> list;
	private JspWriter out;
	private String page_btn_HTML = "";
	private Date cur;
	private SimpleDateFormat fmt;
	private int errno = 0;
	
	public LoadCanJoinContestTask(JspWriter out){
		this.out=out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String user_id=(String)request.getSession().getAttribute("handle");
		
		int i, n;
		
		try{
			i = Integer.valueOf(request.getParameter("i"));
			n = Integer.valueOf(request.getParameter("n")) + 1;
		}catch(Exception e){
			errno = 1;
			return ;
		}
		
		if(i<0 || n<=0){
			errno = 1;
			return ;
		}
		
		int ii = 0;
		
		List<Season> sea_list=SeasonORM.getInstance().loadJoinedSeason(user_id);
		List<Contest> cot_list=ContestORM.getInstance().loadAllContest();
		list=new ArrayList<Contest>();
		
		for(Contest cot:cot_list){
			Set<Integer> s=new HashSet<Integer>();
			int[] tmp=cot.parseSeason_list();
			for(int j=0;j<tmp.length;j++) s.add(tmp[j]);
			boolean can=false;
			
			for(Season sea:sea_list){
				if(s.contains(sea.getSea_id())){
					can=true;
					break;
				}
			}
			if(can){
				if(ii++>=i){
					if(--n==0) break;
					list.add(cot);
				}
			}
		}
		
		int m = 15;
		
		if(i>0) 
			page_btn_HTML+="<li><a href=\"./contest_list.jsp?i="+(i-m)+"&n="+m+"\">上一页</a></li>";
		if(n==0) 
			page_btn_HTML+="<li><a href=\"./contest_list.jsp?i="+(i+m)+"&n="+m+"\">下一页</a></li>";
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		if(errno!=0) return ;
		
		fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		cur = new Date(System.currentTimeMillis());
		
		for(Contest cot:list){
			out.println(getHTML(cot));
		}
	}
	
	private String getHTML(Contest c){
		String HTML = "";
		HTML += "<div class=\"cot_item\">";
		HTML += "<a href=\"./contest_page.jsp?cot_id="+c.getCot_id()+"\" class=\"list-group-item\">";
		HTML += "<table class=\"cot_item\"><tr><td width=\"100%\">";
		HTML +=	"<div class=\"cot_name\"><h3>"+c.getCot_name()+"</h3></div>";
		HTML +=	"<div class=\"cot_status\"><h4><span class=\"label label-";
		
		if(c.getEnd_time().before(cur)) HTML += "default"+"\">已结束";
		else if(c.getStart_time().before(cur)) HTML += "warning"+"\">进行中";
		else HTML += "primary"+"\">未开始";
			
		HTML += "</span></h4></div></td></tr><tr><td align=\"left\">";
		
		int dur=(int)((c.getEnd_time().getTime()-c.getStart_time().getTime())/(1000*60));
		
		HTML += "<p><strong>开始时间：</strong>"+fmt.format(c.getStart_time())+"&nbsp;&nbsp;<strong>结束时间：</strong>"+
				fmt.format(c.getEnd_time())+"&nbsp;&nbsp;<strong>时长：</strong>"+
				dur + " minutes</p></td></tr></table></a></div><br/>";
		
		return HTML;
	}
	
	public String getPageBtnHTML(){
		return this.page_btn_HTML;
	}
	
	public int getErrno(){
		return this.errno;
	}
}