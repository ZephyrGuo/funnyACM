package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import base.ObjectORM;
import base.PooledConnection;

public class TaskTemplateORM extends ObjectORM {
	private static TaskTemplateORM instance;
	private int task_tpl_cnt = -1;
	
	public synchronized static TaskTemplateORM getInstance(){
		if(instance==null) instance=new TaskTemplateORM();
		return instance;
	}
	
	public boolean save(TaskTemplate tpl){
		String sql = "insert into task_template (prb_id_list,task_tpl_name) values (?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, tpl.getPrb_list());
			pst.setString(2, tpl.getTask_tpl_name());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
		
		task_tpl_cnt ++;
		
		return true;
	}
	
	public TaskTemplate loadById(int task_tpl_id){
		String sql = "select * from task_template where task_tpl_id = ?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		ResultSet res = null;
			
		TaskTemplate task_tpl = new TaskTemplate();
				
		try {
			pst.setInt(1, task_tpl_id);
			res = pst.executeQuery();
			
			if(!res.next()) return null;
			
			task_tpl.setTask_tpl_id(res.getInt(1));
			task_tpl.setPrb_list(res.getString(2));
			task_tpl.setTask_tpl_name(res.getString(3));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			con.close();
		}

		return task_tpl;	
	}
	
	public boolean update(TaskTemplate t){
		String sql = "update task_template set prb_id_list = ?, task_tpl_name = ? where task_tpl_id = ?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		ResultSet res = null;
			
		TaskTemplate task_tpl = new TaskTemplate();
				
		try {
			pst.setString(1, t.getPrb_list());
			pst.setString(2, t.getTask_tpl_name());
			pst.setInt(3, t.getTask_tpl_id());
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
	
	public List<Problem> loadProblemList(int task_tpl_id){
		TaskTemplate t = loadById(task_tpl_id);
		
		String[] prb_id_list = t.getPrb_list().split(";");
		
		List<Problem> list = new ArrayList<Problem>();
		
		for(String s : prb_id_list){
			int prb_id;
			
			try{
				 prb_id = Integer.parseInt(s);
			}catch(NumberFormatException e){
				System.out.println("task_tpl_id:" + task_tpl_id + " prb_id_list parse error.");
				break;
			}
			
			Problem c = ProblemORM.getInstance().LoadDetailByPrbId(prb_id);
			c.setPrb_id(prb_id);
			list.add(c);
		}
		
		return list;
	}
	
	public List<TaskTemplate> findByName(String task_name){
		String sql = "select * from task_template where task_tpl_name like ? and task_tpl_id>2";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		ResultSet res = null;
			
		List<TaskTemplate> list = new ArrayList<TaskTemplate>(); 
		
		try {
			pst.setString(1, "%"+task_name+"%");
			res = pst.executeQuery();
			
			while(res.next()){
				TaskTemplate tpl = new TaskTemplate();
				tpl.setTask_tpl_id(res.getInt(1));
				tpl.setPrb_list(res.getString(2));
				tpl.setTask_tpl_name(res.getString(3));
				list.add(tpl);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

		return list;			
	}
	
	public List<TaskTemplate> loadByPaging(int i,int n){
		String sql = "select * from task_template where task_tpl_id>2 order by task_tpl_id DESC limit ?,?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		ResultSet res = null;
			
		List<TaskTemplate> list = new ArrayList<TaskTemplate>(); 
		
		try {
			pst.setInt(1, i);
			pst.setInt(2, n);
			res = pst.executeQuery();
			
			while(res.next()){
				TaskTemplate tpl = new TaskTemplate();
				tpl.setTask_tpl_id(res.getInt(1));
				tpl.setPrb_list(res.getString(2));
				tpl.setTask_tpl_name(res.getString(3));
				list.add(tpl);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

		return list;			
	}
	
	public int getTaskTplCnt(){
		if(task_tpl_cnt!=-1){
			return task_tpl_cnt;
		}
		
		String sql = "select count(*) from task_template";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		ResultSet res = null;

		try {
			res = pst.executeQuery();
			
			if(res.next()){
				task_tpl_cnt = res.getInt(1) - 3;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			task_tpl_cnt = 0;
		} finally {
			con.close();
		}
		
		return task_tpl_cnt;
	}
}
