package DB;


public class Section {
	
	private int status; //用户这章节完成情况
	private String[] prb_id_list; //题目id列表
	private String[] prb_name_list; //题目名字列表
	
	public boolean isComplete(){
		int mask = ((1<<4)-1);
		return (mask & status) == mask;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String[] getPrb_id_list() {
		return prb_id_list;
	}
	public void setPrb_id_list(String[] prb_id_list) {
		this.prb_id_list = prb_id_list;
	}
	public String[] getPrb_name_list() {
		return prb_name_list;
	}
	public void setPrb_name_list(String[] prb_name_list) {
		this.prb_name_list = prb_name_list;
	}
	
	
}
