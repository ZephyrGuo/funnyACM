package event;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.TaskInfo;
import DB.UserDoTaskORM;
import base.Task;

public class LoadTaskListTask extends Task {
	private List<Map.Entry<TaskInfo,Boolean>> list;
	private JspWriter out;
	private int errno = 0;
	
	public LoadTaskListTask(JspWriter out){
		this.out = out;
	}
	
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		String user_id = (String)request.getSession().getAttribute("handle");
		int i, n;
		
		try{
			i = Integer.valueOf(request.getParameter("i"));
			n = Integer.valueOf(request.getParameter("n"));
		}catch(Exception e){
			errno = 1;
			return ;
		}
		
		if(i<0 || n<=0){
			errno = 1;
			return ;
		}
		
		list = UserDoTaskORM.getInstance().loadTasksByUser(user_id,i,n);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		if(errno!=0) return ;
		
		for(Map.Entry<TaskInfo, Boolean> pair : list){
			String HTML;
			
			if(pair.getKey().getStart_time().after(new Date(System.currentTimeMillis())))
				continue;
			
			if(pair.getValue()) HTML=completedTaskHTML(pair.getKey());
			else HTML=uncompletedTaskHTML(pair.getKey());
			
			out.println(HTML);
		}
		
	}
	
	private String completedTaskHTML(TaskInfo t){
		
		String HTML = taskHTML(t);

        HTML+="<div align=\"right\">";    
    	HTML+="<p class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></p>";
        HTML+="</div></div></li>";
        
        return HTML;
	}
	
	private String uncompletedTaskHTML(TaskInfo t){
		
		String HTML = taskHTML(t);
		
        HTML+="</div></li>";
        
        return HTML;
	}
	
	private String taskHTML(TaskInfo t){
		String HTML = new String();
		int mask = t.getMustDoMask();
		
        HTML+="<li>";
        HTML+="<time class=\"cbp_tmtime\">";
        
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        Date time = new Date(t.getStart_time().getTime());
        
        HTML+="<span>"+fmt.format(time)+"</span>";
        
        fmt = new SimpleDateFormat("yy/MM/dd");
        HTML+="<span>"+fmt.format(time)+"</span></time>";
        
        HTML+="<div class=\"cbp_tmicon cbp_tmicon-screen\"></div><div class=\"cbp_tmlabel\">";
        HTML+="<a href=\"./training_page.jsp?task_id="+t.getTask_id()+"\">"; 
        HTML+="<h2>" + t.getTask_name() + "</h2>";
        HTML+="<p>" + t.getTask_description() + "</p></a>";
        HTML+="<p>" + "至少完成"+t.getMustDoCnt()+"题，必做题:{";
         
        for(int i=0;i<26;i++){
        	if((mask & (1<<i))!=0)
        		HTML+=(char)('A'+i);
        }
        HTML+="}</p>";
        
        return HTML;
	}
	
	public int getErrno(){
		return this.errno;
	}
}
