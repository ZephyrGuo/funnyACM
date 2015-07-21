package DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import base.OnlineJudge;

public class Board {
	private Man[] sorted;
	private Contest c;
	Map<String,Man> mans;
	
	public Board(Contest c){
		this.c=c;
		mans= new Hashtable<String,Man>();	
	}
	
	
	public void read(ResultSet res) throws SQLException{
		while(res.next()){
			Timestamp smt_time=res.getTimestamp(1);
			String user=res.getString(2);
			String jdg_res=res.getString(3);
			int prb_id=res.getInt(4);

			if(!mans.containsKey(user)){
				mans.put(user, new Man(user,c.parsePrb_list().length));
			}
			update(mans.get(user),smt_time,jdg_res,prb_id);
		}
	
		List<Man> tmp = new ArrayList<Man>(); 
		
		for(Map.Entry<String, Man> ele: mans.entrySet()){
			tmp.add(ele.getValue());
		}
		
		sorted=tmp.toArray(new Man[tmp.size()]);
		Arrays.sort(sorted);
		
		for(int i=0,j=1;i<sorted.length;i++){
			if(i>0 && sorted[i].compareTo(sorted[i-1])==1) j++;					
			sorted[i].setRank(j);
		}
	}
	
	private int offset(Timestamp now) {
		return (int) Math.abs((now.getTime()-c.getStart_time().getTime())/60000);
	}
	
	private void update(Man man,Timestamp smt_time,String jdg_res,int prb_id){
		if(jdg_res.equals("") || jdg_res.equals(OnlineJudge.JE)){
			return ;
		}
		
		int idx = c.getIndexOfProb(prb_id);
		
		if(man.isAC(idx)){
			return ;
		}
		
		if(jdg_res.equals(OnlineJudge.AC)){
			man.ACnum_add();
			man.tryTimes_add(idx);
			man.setACTime(idx, offset(smt_time));
		}else{	
			man.tryTimes_add(idx);
		}
	}
	
	/**
	 * column-meaning from left to right:<br/>
	 * rank name AC_count penalty problem-A problem-B ...
	 * @return List<String>[]
	 */
	public List<String>[] getBoard(){
		List<String>[] table=new ArrayList[sorted.length];
		
		for(int i=0;i<sorted.length;i++){
			List<String> tr=new ArrayList<String>();
			tr.add(String.valueOf(sorted[i].getRank()));
			tr.add(sorted[i].getName());
			tr.add(String.valueOf(sorted[i].getACnum()));
			tr.add(String.valueOf(sorted[i].getPenalty()));
			int n=c.parsePrb_list().length;
			for(int j=0;j<n;j++){
				tr.add(sorted[i].getProbInfo(j));
			}
			table[i]=tr;
		}
		
		return table;
	}
	
	public String getUserId(int i){
		if(i<0 || i>=sorted.length) return "";
		return sorted[i].getUserId();
	}
	
	private class Man implements Comparable{
		private String id;
		private int ACnum;
		private int penalty;
		private int rank;
		private int[] try_times;
		private int[] AC_time;
		
		
		public Man(){
			super();
		}
		
		public Man(String id,int prob_num){
			super();
			this.ACnum=0;
			this.penalty=0;
			this.try_times=new int[prob_num];
			this.AC_time=new int[prob_num];
			for(int i=0;i<AC_time.length;i++){
				AC_time[i]=-1;
			}
			this.id=id;
		}
		
		@Override
		public int compareTo(Object arg0) {
			Man o = (Man)arg0;
			
			if(this.ACnum>o.ACnum){
				return -1;
			}else if(this.ACnum==o.ACnum){
				if(this.penalty<o.penalty){
					return -1;
				}else if(this.penalty>o.penalty){
					return 1;
				}
			}else{
				return 1;
			}
			
			return 0;
		}
		
		public void ACnum_add(){
			this.ACnum++;
		}
			 
		public void tryTimes_add(int prob_id){
			try_times[prob_id]++;
		}
		
		public void setACTime(int prob_id,int now){
			AC_time[prob_id]=now;
			penalty+=now+(try_times[prob_id]-1)*20;
		}
		
		public String getName(){
			return UserORM.getInstance().loadById(id).getUser_name();
		}
		
		public String getUserId(){
			return this.id;
		}
		
		public int getPenalty(){
			return this.penalty;
		}
		
		public String getProbInfo(int idx){
			if(AC_time[idx]!=-1){
				return "<strong><font color=\"#5cb85c\">"+try_times[idx]+""
						+ " - "+AC_time[idx]+"</font></strong>";
			}else{
				if(try_times[idx]>0){
					return "<strong><font color=\"#d9534f\">-"+try_times[idx]+"</font></strong>";
				}else{
					return "0"; 
				}
			}
		}
		
		public int getACnum(){
			return this.ACnum;
		}
		
		public boolean isAC(int idx){
			return this.AC_time[idx]!=-1;
		}
		
		public void setRank(int r){
			this.rank=r;
		}
		
		public int getRank(){
			return this.rank;
		}
	}
}



