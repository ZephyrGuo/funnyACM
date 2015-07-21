package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import base.ObjectORM;
import base.PooledConnection;


public class SubmitRecordORM extends ObjectORM{
	private static int smt_id; 
	private static SubmitRecordORM instance;

	private SubmitRecordORM(){
		super();
		this.smt_id=findLastSmt_id();
		System.out.println("init SubmitRecordORM, smt_id="+this.smt_id);
	}
	
	public synchronized static SubmitRecordORM getInstance(){
		if(instance==null) instance=new SubmitRecordORM();
		return instance;
	}
	
	public boolean save(SubmitRecord obj){
		if(!insert_submit_record(obj)) return false;
		
		if(!insert_submit_code(obj)){
			del_submit_record(obj.getSmt_id());
			return false;
		}
			
		if(!insert_judge_result(obj)){
			del_submit_record(obj.getSmt_id());
			del_submit_code(obj.getSmt_id());
			return false;
		}
			
		return true;
	}
	
	
	public String loadCompileDetail(int smt_id){		
		String sql = "select jdg_detail from judge_result where smt_id = ?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);

		try {
			pst.setInt(1, smt_id);
			ResultSet res=pst.executeQuery();
			
			if(res.next()){
				return res.getString(1);
			}
			
			return "";
					
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		} finally {
			con.close();
		}
		
	}
	
	public String loadCode(int smt_id){		
		String sql = "select code from submit_code where smt_id = ?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);

		try {
			pst.setInt(1, smt_id);
			ResultSet res=pst.executeQuery();
			
			if(res.next()){
				return res.getString(1);
			}
			
			return "";
					
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		} finally {
			con.close();
		}
		
	}
	
	public List<SubmitRecord> loadContestHistory(String user_id,int prb_id,int cot_id){
		String sql=
			"select submit_record.smt_id,jdg_res,smt_time,jdg_run_time,jdg_run_memory "+
			"from judge_result,submit_record,contest_submit "+
			"where smt_user=? "+
			"and prb_id=? "+
			"and cot_id=? "+
			"and contest_submit.smt_id=submit_record.smt_id "+ 
			"and submit_record.smt_id=judge_result.smt_id "+
			"order by smt_time DESC ";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		List<SubmitRecord> list=new ArrayList<SubmitRecord>();
		
		try {
			pst.setString(1, user_id);
			pst.setInt(2, prb_id);
			pst.setInt(3, cot_id);
			ResultSet res=pst.executeQuery();
			
			while(res.next()){
				SubmitRecord sr=new SubmitRecord();
				sr.setSmt_id(res.getInt(1));
				sr.setResult(res.getString(2));
				sr.setSmt_time(res.getTimestamp(3));
				sr.setRunTime(res.getString(4));
				sr.setRunMemory(res.getString(5));
				list.add(sr);
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
	
	public SubmitRecord loadById(int smt_id){
		String sql = "select * from submit_record where smt_id = ?";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);

		SubmitRecord sr = new SubmitRecord();
		
		try {
			pst.setInt(1, smt_id);
			ResultSet res=pst.executeQuery();
			
			if(res.next()){
				sr.setSmt_id(res.getInt(1));
				sr.setSmt_oj_id(res.getString(2));
				sr.setSmt_time(res.getTimestamp(3));
				sr.setSmt_user(res.getString(4));
				sr.setSmt_oj(res.getString(5));
				sr.setPrb_id(res.getInt(6));
				
				return sr;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return null;
	}
	
	public List<SubmitRecord> loadTaskHistory(String user_id,int prb_id,int task_id){
		String sql=
			"select submit_record.smt_id,jdg_res,smt_time,jdg_run_time,jdg_run_memory "+
			"from judge_result,submit_record,task_submit "+
			"where smt_user=? "+
			"and prb_id=? "+
			"and task_id=? "+
			"and task_submit.smt_id=submit_record.smt_id "+
			"and submit_record.smt_id=judge_result.smt_id "+
			"order by smt_time DESC ";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		List<SubmitRecord> list=new ArrayList<SubmitRecord>();
		
		try {
			pst.setString(1, user_id);
			pst.setInt(2, prb_id);
			pst.setInt(3, task_id);
			ResultSet res=pst.executeQuery();
			
			while(res.next()){
				SubmitRecord sr=new SubmitRecord();
				sr.setSmt_id(res.getInt(1));
				sr.setResult(res.getString(2));
				sr.setSmt_time(res.getTimestamp(3));
				sr.setRunTime(res.getString(4));
				sr.setRunMemory(res.getString(5));
				list.add(sr);
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
		
	private boolean insert_submit_record(SubmitRecord obj){
		String sql = "insert into submit_record values(?,?,?,?,?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, obj.getSmt_id());
			pst.setString(2, obj.getSmt_oj_id());
			pst.setTimestamp(3, obj.getSmt_time());
			pst.setString(4, obj.getSmt_user());
			pst.setString(5, obj.getSmt_oj());
			pst.setInt(6, obj.getPrb_id());
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
	
	private void del_submit_record(int smt_id){
		String sql = "delete from submit_record where smt_id = ?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, smt_id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}	
	}
	
	private boolean insert_submit_code(SubmitRecord obj){
		String sql = "insert into submit_code values(?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, obj.getSmt_id());
			pst.setString(2, obj.getCode());
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
	
	private void del_submit_code(int smt_id){
		String sql = "delete from submit_code where smt_id = ?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, smt_id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}	
	}
	
	private boolean insert_judge_result(SubmitRecord obj){
		String sql = "insert into judge_result values(?,?,?,?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, obj.getSmt_id());
			pst.setString(2, obj.getResult());
			pst.setString(3, obj.getDetail());
			pst.setString(4, obj.getRunMemory());
			pst.setString(5, obj.getRunTime());
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
	
	private int findLastSmt_id(){
		String sql = "select max(smt_id) from submit_record";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			ResultSet res=pst.executeQuery();
			if(res.next()){
				return res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.close();
		}	
		return 0;		
	}
	
	public synchronized int getSmt_id(){
		return (++smt_id);
	}
	
}
