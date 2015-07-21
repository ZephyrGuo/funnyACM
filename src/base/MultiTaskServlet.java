package base;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class MultiTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private Map<String,String> methods;
	
    public MultiTaskServlet() {
        super();
    }
    
    public void init(){
        methods=new HashMap<String,String>();
        initRegist();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String req=request.getParameter("req");
		
		if(req==null){
			System.out.println("no request method");
			response.getWriter().print("Error,no request method");
			return ;
		}
		
		String taskName=methods.get(req);
		
		if(taskName==null){
			System.out.println("no regist the method '"+taskName+"'");
			response.getWriter().print("Error,can't find such request method");
			return ;
		}
		
		Task task=getTaskByName(taskName);
		
		if(task==null){
			System.out.println("no the task class '"+req+"'");
			response.getWriter().print("Error,can't find such request method");
			return ;
		}
		
		task.doRequest(request);
		task.doResponse(response);
	}
	
	
	private Task getTaskByName(String name){
		try {
			return (Task)Class.forName("event."+name).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void registTask(String reqName,String taskName){
		methods.put(reqName,taskName);
	}
		
	protected abstract void initRegist();
}
