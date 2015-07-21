package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import base.ObjectORM;
import base.PooledConnection;
import common.StringTool;

public class UserORM extends ObjectORM {
	
	private static UserORM instance;
	
	public synchronized static UserORM getInstance(){
		if(instance==null) instance=new UserORM();
		return instance;
	}
	
	public boolean save(User u){
		String sql = "insert into user values(?,?,?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, u.getUser_id());
			pst.setInt(2, u.getUser_type());
			pst.setString(3, u.getUser_psw());
			pst.setString(4, u.getUser_name());
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
	
	public boolean check(User u){
		String sql = "select *  from user where user_id=? and user_psw=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, u.getUser_id());		
			pst.setString(2, u.getUser_psw());	
			ResultSet res=pst.executeQuery();
			return res.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
	}
	
	public User loadById(String user_id){
		String sql = "select *  from user where user_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, user_id);		
			ResultSet res=pst.executeQuery();
			if(!res.next()) return null;
			User u=new User();
			u.setUser_id(user_id);
			u.setUser_type(res.getInt(2));
			u.setUser_name(res.getString(4));
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return null;
	}
	
	public List<User> findByName(String user_name){
		String sql = "select user_id,user_type,user_name from user where user_name like ?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
			
		List<User> list = new ArrayList<User>();
		
		try {
			pst.setString(1, "%"+user_name+"%");		
			ResultSet res=pst.executeQuery();
			
			while(res.next()){
				User u = new User();
				u.setUser_id(res.getString(1));
				u.setUser_type(res.getInt(2));
				u.setUser_name(res.getString(3));
				list.add(u);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();			
		} finally {
			con.close();
		}
		
		return list;
	}
	
	public boolean updPermission(String user_id,int type){
		String sql = "update user set user_type = ? where user_id = ?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
			
		List<User> list = new ArrayList<User>();
		
		try {
			pst.setInt(1, type);
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
	
	public boolean updUser(User u){
		String sql;
		
		if(u.getUser_psw().equals(""))
			sql = "update user set user_name=?,user_type=? where user_id = ?";
		else
			sql = "update user set user_name=?,user_psw=?,user_type=? where user_id = ?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
			
		try {
			
			pst.setString(1, u.getUser_name());
			if(u.getUser_psw().equals("")){
				pst.setInt(2, u.getUser_type());
				pst.setString(3, u.getUser_id());
			}else{
				pst.setString(2, u.getUser_psw());
				pst.setInt(3, u.getUser_type());
				pst.setString(4, u.getUser_id());
			}
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
