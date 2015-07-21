package DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeasonBoard {
	
	private List<Man> mans;
	
	
	public SeasonBoard(){
		mans=new ArrayList<Man>();
	}
	
	public void read(ResultSet res) throws SQLException{
		
		while(res.next()){
			mans.add(new Man(res.getString(1),res.getInt(2),res.getInt(3),res.getString(4)));
		}
		
		for(int i=0,j=1;i<mans.size();i++){
			if(i>0 && mans.get(i).compareTo(mans.get(i-1))!=0) j++;					
			mans.get(i).setRank(j);
		}
	}
	
	
	public List<String>[] convertSeasonBoard(){
		List<String>[] table=new ArrayList[mans.size()];
		
		for(int i=0;i<mans.size();i++){
			table[i]=mans.get(i).parse();
		}
		
		return table;
	}
	
	public int query_rank(String user_id){
		for(Man m : mans){
			if(m.getUser_id().equals(user_id)) return m.getRank();
		}
		return -1;
	}
	
	private class Man implements Comparable{
		private String user_name;
		private String user_id;
		private int rating;
		private int join_cnt;
		private int rank;
		
		
		public Man(String user_name, int rating, int join_cnt,String user_id) {
			this.user_name = user_name;
			this.rating = rating;
			this.join_cnt = join_cnt;
			this.user_id = user_id;
		}
		
		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}
		public String getUser_name() {
			return user_name;
		}
		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}
		public int getRating() {
			return rating;
		}
		public void setRating(int rating) {
			this.rating = rating;
		}
		public int getJoin_cnt() {
			return join_cnt;
		}
		public void setJoin_cnt(int join_cnt) {
			this.join_cnt = join_cnt;
		}
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		@Override
		public int compareTo(Object o) {
			Man man=(Man)o;
			
			if(this.rating - man.getRating()!=0){
				return this.rating - man.getRating();
			}
			
			return this.join_cnt-man.getJoin_cnt();
		}
		
		public List<String> parse(){
			List<String> list=new ArrayList<String>();
			
			list.add(String.valueOf(this.rank));
			list.add(this.user_name);
			list.add(String.valueOf(this.rating));
			list.add(String.valueOf(this.join_cnt));
			
			return list;
		}
	}
}
