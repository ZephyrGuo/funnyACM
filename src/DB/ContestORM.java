package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import base.ObjectORM;
import base.PooledConnection;
import common.StringTool;

public class ContestORM extends ObjectORM {
	
	private static ContestORM instance;
	private int contest_cnt = -1;
	
	
	public synchronized static ContestORM getInstance(){
		if(instance==null) instance=new ContestORM();
		return instance;
	}
	
	
	public boolean save(Contest c){
		String sql = "insert into contest_info (start_time,end_time,season_list,cot_name,prb_list) values(?,?,?,?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setTimestamp(1, c.getStart_time());
			pst.setTimestamp(2, c.getEnd_time());
			pst.setString(3, c.getSeason_list());
			pst.setString(4, c.getCot_name());
			pst.setString(5, c.getPrb_list());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
		
		return true;
	}
	
	public boolean update(Contest c){
		String sql = "update contest_info set start_time=?,end_time=?,season_list=?,cot_name=?,prb_list=? where cot_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setTimestamp(1, c.getStart_time());
			pst.setTimestamp(2, c.getEnd_time());
			pst.setString(3, c.getSeason_list());
			pst.setString(4, c.getCot_name());
			pst.setString(5, c.getPrb_list());
			pst.setInt(6,c.getCot_id());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
		
		return true;
	}
	
	public boolean addContestSubmit(int cot_id,int smt_id){
		String sql = "insert into contest_submit values(?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, cot_id);
			pst.setInt(2, smt_id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
		
		return true;
	}
	
	public Contest findById(int cot_id){
		String sql = "select * from contest_info where cot_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		ResultSet res=null;
		try {
			pst.setInt(1, cot_id);
			res=pst.executeQuery();
			if(!res.next()) return null;
			Contest c=new Contest();
			c.setCot_id(cot_id);
			c.setStart_time(res.getTimestamp(2));
			c.setEnd_time(res.getTimestamp(3));
			c.setSeason_list(res.getString(4));
			c.setCot_name(res.getString(5));
			c.setPrb_list(res.getString(6));
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			con.close();
		}
	}
	
	public List<Contest> loadAllContest(){
		String sql = "select * from contest_info order by start_time DESC";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		ResultSet res=null;
		
		List<Contest> list=new ArrayList<Contest>();
		
		try {
			res=pst.executeQuery();
			while(res.next()){
				Contest c=new Contest();
				c.setCot_id(res.getInt(1));
				c.setStart_time(res.getTimestamp(2));
				c.setEnd_time(res.getTimestamp(3));
				c.setSeason_list(res.getString(4));
				c.setCot_name(res.getString(5));
				c.setPrb_list(res.getString(6));
				list.add(c);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return list;
	}
	
	public List<Contest> loadByPaging(int i,int n){
		String sql = "select * from contest_info order by start_time DESC limit ?,?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		ResultSet res=null;
		
		List<Contest> list=new ArrayList<Contest>();
		
		try {		
			pst.setInt(1, i);
			pst.setInt(2, n);			
			res=pst.executeQuery();
			
			while(res.next()){
				Contest c=new Contest();
				c.setCot_id(res.getInt(1));
				c.setStart_time(res.getTimestamp(2));
				c.setEnd_time(res.getTimestamp(3));
				c.setSeason_list(res.getString(4));
				c.setCot_name(res.getString(5));
				c.setPrb_list(res.getString(6));
				list.add(c);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return list;
	}
	
	public List<Problem> loadContestProblemList(int cot_id){
		Contest cot=findById(cot_id);
		int[] prbs=cot.parsePrb_list();
		List<Problem> list=new ArrayList<Problem>();

		for(int i=0;i<prbs.length;i++){
			list.add(ProblemORM.getInstance().LoadDetailByPrbId(prbs[i]));
		}
		
		return list;
	}
	
	public int getContestCnt(){
		if(contest_cnt!=-1) return contest_cnt;
		
		String sql = "select count(*) from contest_info";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		ResultSet res=null;
		
		List<Contest> list=new ArrayList<Contest>();
		
		try {		
			res=pst.executeQuery();
			if(res.next()) contest_cnt = res.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			contest_cnt = 0;
		} finally {
			con.close();
		}
		
		return contest_cnt;
	}
}
