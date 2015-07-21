package event;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.Task;

public class Work {
	
	public static void doRequest(String taskName,HttpServletRequest request) throws IOException{
		Task task=null;
		try {
			task = (Task)Class.forName(taskName).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		task.doRequest(request);
	}
	
	public static void doResponse(String taskName,HttpServletResponse response) throws IOException{
		Task task=null;
		try {
			task = (Task)Class.forName(taskName).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		task.doResponse(response);
	}
}
