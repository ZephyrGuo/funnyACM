package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import base.ObjectORM;
import base.PooledConnection;

public class UserRecordORM extends ObjectORM{
	
	private static UserRecordORM instance;
	
	public synchronized static UserRecordORM getInstance(){
		if(instance==null) instance=new UserRecordORM();
		return instance;
	}
	
	public UserRecord loadById(String user_id){			
		UserRecord r = new UserRecord();
		
		r.setTotal_task(this.query_total_task(user_id));
		r.setTotal_problem(this.query_total_problem(user_id));
		r.setBest_rank(this.query_best_rank(user_id));
		r.setBest_score(this.query_best_score(user_id));
		r.setTotal_score(this.query_total_score(user_id));
		
		return r;
	}
	
	private int query_total_task(String user_id){
		List<Map.Entry<TaskInfo, Boolean>> list = 
				UserDoTaskORM.getInstance().loadTasksByUser(user_id,0,100000);
		int cnt = 0;
		
		for(Map.Entry<TaskInfo, Boolean> p : list){
			if(p.getValue()) cnt++;
		}
		
		return cnt;
	}
	
	private int query_total_problem(String user_id){
		String sql = "select SUM(`status`>>26) from user_do_task where user_id = ?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, user_id);
			
			ResultSet res = pst.executeQuery();		
			if(res.next()) return res.getInt(1);	
			
		} catch (SQLException e) {
			e.printStackTrace();			
		} finally {
			con.close();
		}
		
		return -1;
	}
	
	private int query_best_rank(String user_id){
		String sql="select sea_id from season_rating where user_id=?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		int best_rank = -1;
		
		try {
			pst.setString(1, user_id);
			
			ResultSet res = pst.executeQuery();
			
			while(res.next()){
				int sea_id = res.getInt(1);
				SeasonBoard b = SeasonBoardORM.getInstance().loadById(sea_id);
				
				best_rank = Math.max(best_rank, b.query_rank(user_id));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();			
		} finally {
			con.close();
		}
		
		return best_rank;
	}
	
	private int query_total_score(String user_id){
		String sql = "select SUM(rating) from season_rating where user_id = ?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, user_id);
			
			ResultSet res = pst.executeQuery();		
			if(res.next()) return res.getInt(1);	
			
		} catch (SQLException e) {
			e.printStackTrace();			
		} finally {
			con.close();
		}
		
		return -1;			
	}
	
	private int query_best_score(String user_id){
		String sql = "select MAX(rating) from season_rating where user_id = ?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, user_id);
			
			ResultSet res = pst.executeQuery();		
			if(res.next()) return res.getInt(1);	
			
		} catch (SQLException e) {
			e.printStackTrace();			
		} finally {
			con.close();
		}
		
		return -1;		
	}
}
