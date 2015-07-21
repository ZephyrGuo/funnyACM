package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import base.ObjectORM;
import base.PooledConnection;

public class TaskInfoORM extends ObjectORM {
	private static TaskInfoORM instance;
	private static int task_id;
	private int task_cnt = -1;
	
	private TaskInfoORM(){
		super();
		this.init();
		System.out.println("init TaskInfoORM, task_id="+task_id);
	}
	
	public synchronized static TaskInfoORM getInstance(){
		if(instance==null) instance=new TaskInfoORM();
		return instance;
	}
	
	public boolean save(TaskInfo t){
		String sql = "insert into task_info values (?,?,?,?,?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		t.setTask_id(++task_id);
		
		try {
			pst.setInt(1, t.getTask_id());
			pst.setInt(2, t.getCondition_mask());
			pst.setTimestamp(3, t.getStart_time());
			pst.setInt(4, t.getTask_tpl_id());
			pst.setString(5, t.getTask_name());
			pst.setString(6, t.getTask_description());
			pst.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
		
		task_cnt ++;
		
		return true;
	}
	
	public boolean update(TaskInfo t){
		String sql = "update task_info set condition_mask=?,start_time=?,task_tpl_id=?,task_name=?,task_description=? where task_id=?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, t.getCondition_mask());
			pst.setTimestamp(2, t.getStart_time());
			pst.setInt(3, t.getTask_tpl_id());
			pst.setString(4, t.getTask_name());
			pst.setString(5, t.getTask_description());
			pst.setInt(6, t.getTask_id());
			pst.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
				
		return true;		
	}
	
	public int getTaskCnt(){
		if(task_cnt!=-1){
			return task_cnt;
		}
		
		String sql = "select count(*) from task_info";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		ResultSet res = null;

		try {
			res = pst.executeQuery();
			
			if(res.next()){
				task_cnt = res.getInt(1) - 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			task_cnt = 0;
		} finally {
			con.close();
		}
		
		return task_cnt;
	}
	
	public TaskInfo loadById(int task_id){
		String sql = "select * from task_info where task_id = ?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		ResultSet res = null;
			
		TaskInfo t = new TaskInfo();
				
		try {
			pst.setInt(1, task_id);
			res = pst.executeQuery();
			
			if(!res.next()) return null;
			
			t.setTask_id(res.getInt(1));
			t.setCondition_mask(res.getInt(2));
			t.setStart_time(res.getTimestamp(3));
			t.setTask_tpl_id(res.getInt(4));
			t.setTask_name(res.getString(5));
			t.setTask_description(res.getString(6));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			con.close();
		}

		return t;	
	}
	
	public List<TaskInfo> loadTaskByPaging(int i,int n){
		String sql = "select * from task_info where task_id>0" +
					" order by start_time DESC limit ?,?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		ResultSet res = null;
		List<TaskInfo> list = new ArrayList<TaskInfo>();
						
		try {
			pst.setInt(1, i);
			pst.setInt(2, n);
			
			res = pst.executeQuery();
			
			while(res.next()){
				TaskInfo t = new TaskInfo();
				t.setTask_id(res.getInt(1));
				t.setCondition_mask(res.getInt(2));
				t.setStart_time(res.getTimestamp(3));
				t.setTask_tpl_id(res.getInt(4));
				t.setTask_name(res.getString(5));
				t.setTask_description(res.getString(6));	
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

		return list;	
	}
	
	public boolean updateDoTaskStatus(int task_id,String user_id,int status){
		String sql = "update user_do_task set status = ? where task_id = ? and user_id = ?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, status);
			pst.setInt(2, task_id);
			pst.setString(3, user_id);
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
	
	public boolean insertDoTaskStatus(int task_id,String user_id,int status){
		String sql = "insert into user_do_task values(?,?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, task_id);
			pst.setString(2, user_id);
			pst.setInt(3, status);
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
	
	public boolean ishasTask(String user_id,int task_id){
		String sql = "select * from user_do_task where task_id=? and user_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		ResultSet res;
		
		try {
			pst.setInt(1, task_id);
			pst.setString(2, user_id);
			
			res = pst.executeQuery();
			
			return res.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
	}
	
	public boolean insertTaskSubmitRecord(int task_id,int smt_id){
		String sql = "insert into task_submit values(?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, task_id);
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
	
	private void init(){
		String sql = "select max(task_id) from task_info";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		ResultSet res;
		
		try {
			res = pst.executeQuery();
			
			if(res.next()){
				task_id =  res.getInt(1);
			}else{
				task_id = 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			task_id = -1;
		} finally {
			con.close();
		}
	}
	
	public boolean clear(int task_id){
		String sql = "update user_do_task set status=0 where task_id=?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);

		try {
			pst.setInt(1, task_id);
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
