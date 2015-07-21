package DB;

import java.sql.Timestamp;

public class TaskInfo {
	private int task_id;
	private int condition_mask;
	private Timestamp start_time;
	private int task_tpl_id;
	private String task_name;
	private String task_description;
	
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	public int getCondition_mask() {
		return condition_mask;
	}
	public void setCondition_mask(int condition_mask) {
		this.condition_mask = condition_mask;
	}
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public int getTask_tpl_id() {
		return task_tpl_id;
	}
	public void setTask_tpl_id(int task_tpl_id) {
		this.task_tpl_id = task_tpl_id;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public String getTask_description() {
		return task_description;
	}
	public void setTask_description(String task_description) {
		this.task_description = task_description;
	}
	
	public boolean isComplete(int status){		
		int ac_mask = (1<<26)-1;
		int ac_cnt_mask = Integer.MAX_VALUE - ac_mask;
		
		boolean cod1 = ((condition_mask & ac_mask) & status) == (condition_mask & ac_mask);
		boolean cod2 = (condition_mask & ac_cnt_mask) <= (status & ac_cnt_mask);
		
		return cod1 & cod2;
	}
	
	public int getMustDoCnt(){
		return this.condition_mask >> 26;
	}
	
	public int getMustDoMask(){
		return this.condition_mask & ((1<<26)-1);
	}
}
