package common;

public class ThreadTool {
	
	public static void runTask(Runnable task){
		new Thread(task).start();
	}
	
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
