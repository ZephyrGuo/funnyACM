package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.ObjectORM;
import base.PooledConnection;

public class UserDoTaskORM extends ObjectORM {

	private static UserDoTaskORM instance;
	
	public synchronized static UserDoTaskORM getInstance(){
		if(instance==null) instance=new UserDoTaskORM();
		return instance;
	}
	
	public List<Map.Entry<TaskInfo,Boolean>> loadTasksByUser(String user_id,int i,int n){
		String sql="select task_info.task_id,task_name,task_description,start_time,task_tpl_id,condition_mask,`status` "+
				" from task_info, user_do_task "+
				" where user_do_task.task_id = task_info.task_id " +
				" and user_do_task.user_id = ? and user_do_task.task_id>0" +
				" order by start_time DESC" +
				" limit ?,?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		ResultSet res=null;
		List<Map.Entry<TaskInfo,Boolean>> list = new ArrayList<Map.Entry<TaskInfo,Boolean>>();
		
		try {
			pst.setString(1, user_id);
			pst.setInt(2, i);
			pst.setInt(3, n);
						
			res=pst.executeQuery();
			
			while(res.next()){
				TaskInfo t = new TaskInfo();
				t.setTask_id(res.getInt(1));
				t.setTask_name(res.getString(2));
				t.setTask_description(res.getString(3));
				t.setStart_time(res.getTimestamp(4));
				t.setTask_tpl_id(res.getInt(5));
				t.setCondition_mask(res.getInt(6));
				
				Map.Entry<TaskInfo,Boolean> pair = 
						new AbstractMap.SimpleEntry<TaskInfo,Boolean>(t,t.isComplete(res.getInt(7)));
				
				list.add(pair);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			con.close();
		}
		
		return list;
	}
	
	public int loadStatus(int task_id,String user_id){
		String sql="select status from user_do_task where task_id=? and user_id=?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
	
		ResultSet res = null;
		
		try {
			pst.setInt(1, task_id);
			pst.setString(2, user_id);
			res=pst.executeQuery();
			
			if(res.next()) return res.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return -1;
	}
	
	public List<Map.Entry<String,Integer>> loadStatusByTaskId(int task_id){
		String sql= "select user_name,`status` from user_do_task,user" +
				" where user_do_task.user_id = user.user_id and task_id = ?" +
				" order by `status` DESC";
		
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
	
		ResultSet res = null;
		
		List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>();
		
		try {
			pst.setInt(1, task_id);
			res=pst.executeQuery();
			
			while(res.next()){
				list.add(new AbstractMap.SimpleEntry<String, Integer>(res.getString(1),res.getInt(2)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return list;
	}
	
	public int countUserDoTask(String user_id){
		String sql = "select count(*) from user_do_task where user_id=?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
	
		ResultSet res = null;
		
		try {
			pst.setString(1, user_id);
			res=pst.executeQuery();
			
			if(res.next()){
				return res.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return 0;
	}
}
