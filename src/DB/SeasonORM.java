package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import base.ObjectORM;
import base.PooledConnection;

public class SeasonORM extends ObjectORM {
	
	private static SeasonORM instance;
	
	public synchronized static SeasonORM getInstance(){
		if(instance==null) instance=new SeasonORM();
		return instance;
	}
	
	public boolean save(Season s){
		String sql = "insert into season_info (sea_des,sea_name,sea_len,sea_create) values(?,?,?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, s.getSea_des());
			pst.setString(2, s.getSea_name());
			pst.setInt(3, s.getSea_len());
			pst.setTimestamp(4, s.getSea_create());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
		
		return true;
	}
	
	public boolean update(Season s){
		String sql = "update season_info set sea_des=?,sea_name=?,sea_len=? where sea_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, s.getSea_des());
			pst.setString(2, s.getSea_name());
			pst.setInt(3, s.getSea_len());
			pst.setInt(4, s.getSea_id());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
		
		return true;		
	}
	
	public boolean insert_season_apply(int sea_id,String user_id){
		String sql = "insert into season_apply (sea_id,user_id) values(?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, sea_id);
			pst.setString(2, user_id);
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
	
	public List<Season> loadJoinedSeason(String user_id){		
		String sql = "select season_info.sea_id,sea_des,sea_name,sea_len,sea_create"+
				" from season_rating,season_info"+
				" where user_id=? and season_rating.sea_id=season_info.sea_id"+
				" order by sea_create desc";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		List<Season> list=new ArrayList<Season>();
		
		try {
			pst.setString(1, user_id);
			ResultSet res=pst.executeQuery();
			while(res.next()){
				Season s=new Season();
				s.setSea_id(res.getInt(1));
				s.setSea_des(res.getString(2));
				s.setSea_name(res.getString(3));
				s.setSea_len(res.getInt(4));
				s.setSea_create(res.getTimestamp(5));
				list.add(s);
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			con.close();
		}
		
		return list;
	}
	
	public List<Season> loadCanApplySeason(String user_id){		
		String sql = "select sea_id,sea_des,sea_name,sea_len,sea_create"+
					" from season_info"+
					" where not exists("+
					"	select *"+
					"	from season_rating"+
					"	where user_id=? and season_rating.sea_id=season_info.sea_id"+
					")"+
					" and not exists("+
					"	select *"+
					"    from season_apply"+
					"	where user_id=? and season_apply.sea_id=season_info.sea_id"+
					") order by sea_create desc";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		List<Season> list=new ArrayList<Season>();
		
		try {
			pst.setString(1, user_id);
			pst.setString(2, user_id);
			ResultSet res=pst.executeQuery();

			while(res.next()){
				Season s=new Season();
				s.setSea_id(res.getInt(1));
				s.setSea_des(res.getString(2));
				s.setSea_name(res.getString(3));
				s.setSea_len(res.getInt(4));
				s.setSea_create(res.getTimestamp(5));
				list.add(s);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			con.close();
		}
		
		return list;
	}
	
	public Season findbyid(int sea_id){
		String sql = "select * from season_info where sea_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, sea_id);
			ResultSet res=pst.executeQuery();
			if(!res.next()) return null;
			Season s=new Season();
			s.setSea_id(res.getInt(1));
			s.setSea_des(res.getString(2));
			s.setSea_name(res.getString(3));
			s.setSea_len(res.getInt(4));
			s.setSea_create(res.getTimestamp(5));
			return s;	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			con.close();
		}				
	}
	
	public List<Season> findbyName(String sea_name){
		String sql = "select * from season_info where sea_name like ?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		List<Season> list = new ArrayList<Season>();
		
		try {
			pst.setString(1, "%"+sea_name+"%");
			ResultSet res=pst.executeQuery();
			
			while(res.next()){
				Season s=new Season();
				s.setSea_id(res.getInt(1));
				s.setSea_des(res.getString(2));
				s.setSea_name(res.getString(3));
				s.setSea_len(res.getInt(4));
				s.setSea_create(res.getTimestamp(5));
				
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return list;
	}
	
	public boolean dealApply(String user_id,int sea_id,int operator){
		if(operator==1){
			return pass(user_id,sea_id);
		}else{
			return nopass(user_id,sea_id);
		}
	}
	
	public boolean calRating(int sea_id,String user_id,int add_score){
		String sql = "update season_rating set rating = rating + ?, join_cnt = join_cnt + ? "+
				"where sea_id = ? and user_id = ?";
		PooledConnection con;
		con = this.getConnection();
		PreparedStatement pst = con.getPrepareStatement(sql);
		
		try{
			pst.setInt(1, add_score);
			pst.setInt(2, 1);
			pst.setInt(3,sea_id);
			pst.setString(4, user_id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
		return true;
	}
	
	public List<Season> loadAllSeason(){
		String sql = "select * from season_info order by sea_create DESC";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		List<Season> list = new ArrayList<Season>();
		
		try {
			ResultSet res=pst.executeQuery();
			
			while(res.next()){
				Season s=new Season();
				s.setSea_id(res.getInt(1));
				s.setSea_des(res.getString(2));
				s.setSea_name(res.getString(3));
				s.setSea_len(res.getInt(4));
				s.setSea_create(res.getTimestamp(5));
				
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return list;		
	}

	public List<ApplySeason> loadAllApply(){
		String sql = "SELECT season_apply.sea_id,season_apply.user_id,`status`,user_name,sea_name FROM season_apply,user,season_info"
				+ " where user.user_id = season_apply.user_id"
				+ " and season_info.sea_id = season_apply.sea_id";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		List<ApplySeason> list = new ArrayList<ApplySeason>();
		
		try {
			ResultSet res=pst.executeQuery();
			
			while(res.next()){
				ApplySeason s = new ApplySeason();
				
				s.setSea_id(res.getInt(1));
				s.setUser_id(res.getString(2));
				s.setStatus(res.getInt(3));
				s.setUser_name(res.getString(4));
				s.setSea_name(res.getString(5));
				
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return list;		
	}
	
	private boolean pass(String user_id,int sea_id){
		PooledConnection con;
		con=this.getConnection();
		
		try {
			con.setAutoCommit(false);
			
			String sql = "insert into season_rating values(?,?,0,0)";		
			PreparedStatement pst=con.getPrepareStatement(sql);
		
			pst.setInt(1, sea_id);
			pst.setString(2, user_id);
			pst.executeUpdate();
			
			sql = "delete from season_apply where sea_id=? and user_id=?";
			pst = con.getPrepareStatement(sql);
			
			pst.setInt(1, sea_id);
			pst.setString(2, user_id);
			pst.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
		
		return true;
	}
	
	private boolean nopass(String user_id,int sea_id){
		String sql = "delete from season_apply where sea_id=? and user_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, sea_id);
			pst.setString(2, user_id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
		
		return true;
	}
}
