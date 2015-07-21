package kernel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import common.Configuration;
import common.ThreadTool;
import base.JudgeReq;
import base.OnlineJudge;

public class Judge {
	private static Judge instance;
	private ExecutorService pool;
	private Map<String,OJMainThread> threads;
	
	private Judge(){
		pool=Executors.newCachedThreadPool();
		
		// create oj main thread
		threads=new HashMap<String, OJMainThread>();	
		Properties conf=Configuration.load("./conf/common.properties");
		String[] list=conf.getProperty("oj_list").split(";");
		for(int i=0;i<list.length;i++){
			threads.put(list[i],new OJMainThread(getOjByName(list[i]),pool));
			ThreadTool.runTask(threads.get(list[i]));
			threads.get(list[i]).open();
		}
	}
	
	public synchronized static Judge getInstance(){
		if(instance==null) instance=new Judge();
		return instance;
	}
	
	public int judge(JudgeReq req){
		return threads.get(req.getOJ()).addRequest(req);
	}
	
	private OnlineJudge getOjByName(String name){
		try {
			return (OnlineJudge)Class.forName("oj."+name).newInstance();
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
		return null;
	}
}
