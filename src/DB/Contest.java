package DB;

import java.sql.Timestamp;

public class Contest {
	private int cot_id;
	private Timestamp start_time;
	private Timestamp end_time;
	private String season_list;
	private String cot_name;
	private String prb_list;
	
	
	
	public String getPrb_list() {
		return prb_list;
	}
	public void setPrb_list(String prb_list) {
		this.prb_list = prb_list;
	}
	public String getCot_name() {
		return cot_name;
	}
	public void setCot_name(String cot_name) {
		this.cot_name = cot_name;
	}
	public int getCot_id() {
		return cot_id;
	}
	public void setCot_id(int cot_id) {
		this.cot_id = cot_id;
	}
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public String getSeason_list() {
		return season_list;
	}
	public void setSeason_list(String season_list) {
		this.season_list = season_list;
	}
	
	
	public int[] parsePrb_list(){
		String[] str_list=this.prb_list.split(";");
		int[] int_list=new int[str_list.length];
		for(int i=0;i<int_list.length;i++){
			int_list[i]=Integer.valueOf(str_list[i]);
		}
		return int_list;
	}
	
	public int[] parseSeason_list(){
		String[] str_list=this.season_list.split(";");
		int[] int_list=new int[str_list.length];
		for(int i=0;i<int_list.length;i++){
			int_list[i]=Integer.valueOf(str_list[i]);
		}
		return int_list;
	}
	
	public int getIndexOfProb(int prb_id){
		int[] prbs=this.parsePrb_list();
		for(int i=0;i<prbs.length;i++){
			if(prb_id==prbs[i]){
				return i;
			}
		}
		return -1;
	}
}
