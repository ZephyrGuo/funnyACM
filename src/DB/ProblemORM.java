package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import base.ObjectORM;
import base.PooledConnection;

public class ProblemORM extends ObjectORM {

	private static ProblemORM instance;
	private int problemCount = -1;
	
	public synchronized static ProblemORM getInstance(){
		if(instance==null) instance=new ProblemORM();
		return instance;
	}
	
	public boolean save(Problem p){
		
		boolean res = true;
		
		if((res=this.insert_problem_info(p))){
			p.setPrb_id(getPrbIdByOJInfo(p));
			res = this.insert_problem_detail(p);
		}
		
		if(!res)
			delete_problem_info(p);
		
		if(res) problemCount++;
		
		return res;
	}
	
	public boolean update(Problem p){
		String sql = "update problem_detail set title=?,prb_detail=?,input_detail=?,output_detail=?,sample_input=?"+
				",sample_output=?,hint_detail=? where prb_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, p.getTitle());
			pst.setString(2, p.getProblemDetail());
			pst.setString(3, p.getInputDetail());
			pst.setString(4, p.getOutputDetail());
			pst.setString(5, p.getSampleInput());
			pst.setString(6, p.getSampleOutput());
			pst.setString(7, p.getHintDetail());
			pst.setInt(8, p.getPrb_id());
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			con.close();
		}
		
		return true;
	}
	
	public List<Problem> loadProbInfoPaging(int i,int n){
		String sql = "select * from problem_info limit ?,?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		List<Problem> list = new ArrayList<Problem>();
		
		try {
			pst.setInt(1, i);
			pst.setInt(2, n);
			ResultSet res = pst.executeQuery();
			
			while(res.next()){
				Problem p = new Problem();
				
				p.setPrb_id(res.getInt(1));
				p.setOj_id(res.getString(2));
				p.setOj_name(res.getString(3));
				p.setSubmit_params(res.getString(4));
				
				list.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return list;
	}
	
	public int countProblem(){
		if(problemCount!=-1) return problemCount;
		
		String sql = "select COUNT(*) from problem_info";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			ResultSet res = pst.executeQuery();
			
			if(res.next()){
				problemCount = res.getInt(1);
				System.out.println("init problemCount="+problemCount);
			}else{
				return -1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			con.close();
		}
		
		return problemCount;		
	}
	
	private boolean insert_problem_info(Problem p){
		String sql = "insert into problem_info (oj_id,oj_name,submit_params) values(?,?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, p.getOj_id());
			pst.setString(2, p.getOj_name());
			pst.setString(3, p.getSubmit_params());
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
	
	private void delete_problem_info(Problem p){
		String sql = "delete from problem_info where oj_name=? and oj_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, p.getOj_name());
			pst.setString(2, p.getOj_id());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.close();
		}
	}
	
	private int getPrbIdByOJInfo(Problem p){
		String sql = "select prb_id from problem_info where oj_name=? and oj_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, p.getOj_name());
			pst.setString(2, p.getOj_id());
			ResultSet res = pst.executeQuery();
			
			if(!res.next()) return -1;
			
			return res.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			con.close();
		}
	}
	
	private boolean insert_problem_detail(Problem p){	
		String sql = "insert into problem_detail values(?,?,?,?,?,?,?,?,?,?)";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, p.getPrb_id());
			pst.setString(2, p.getTitle());
			pst.setString(3, p.getTimeLimit());
			pst.setString(4, p.getMemoryLimit());
			pst.setString(5, p.getProblemDetail());
			pst.setString(6, p.getInputDetail());
			pst.setString(7, p.getOutputDetail());
			pst.setString(8, p.getSampleInput());
			pst.setString(9, p.getSampleOutput());
			pst.setString(10, p.getHintDetail());
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
	
	public boolean exist(Problem p){
		String sql = "select prb_id from problem_info where oj_name=? and oj_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, p.getOj_name());
			pst.setString(2, p.getOj_id());
			ResultSet res=pst.executeQuery();
			return res.next();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true; 
		} finally {
			con.close();
		}

	}
	
	public Problem LoadDetailByPrbId(int prb_id){
		String sql = "select * from problem_detail where prb_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, prb_id);
			ResultSet res=pst.executeQuery();
			if(res.next()){
				Problem p=new Problem();
				
				p.setPrb_id(res.getInt(1));
				p.setTitle(res.getString(2));
				p.setTimeLimit(res.getString(3));
				p.setMemoryLimit(res.getString(4));
				p.setProblemDetail(res.getString(5));
				p.setInputDetail(res.getString(6));
				p.setOutputDetail(res.getString(7));
				p.setSampleInput(res.getString(8));
				p.setSampleOutput(res.getString(9));
				p.setHintDetail(res.getString(10));
				
				return p;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return null;
	}
	
	public Problem LoadInfoByPrbId(int prb_id){
		String sql = "select * from problem_info where prb_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setInt(1, prb_id);
			ResultSet res=pst.executeQuery();
			if(res.next()){
				Problem p=new Problem();	
				
				p.setPrb_id(res.getInt(1));
				p.setOj_id(res.getString(2));
				p.setOj_name(res.getString(3));
				p.setSubmit_params(res.getString(4));
					
				return p;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return null;
	}
	
	public Problem LoadInfoByOJ(String oj_name,String oj_id){
		String sql = "select * from problem_info where oj_name=? and oj_id=?";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		
		try {
			pst.setString(1, oj_name);
			pst.setString(2, oj_id);
			ResultSet res=pst.executeQuery();
			if(res.next()){
				Problem p=new Problem();	
				
				p.setPrb_id(res.getInt(1));
				p.setOj_id(res.getString(2));
				p.setOj_name(res.getString(3));
				p.setSubmit_params(res.getString(4));
					
				return p;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.close();
		}
		
		return null;
	}
}
