package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

import common.ThreadTool;
import base.ObjectORM;
import base.OnlineJudge;
import base.PooledConnection;

public class BoardORM extends ObjectORM {
	
	private static BoardORM instance;
	private final Map<Integer,BoardCache> cache;
	private final int wait_limit = 2500;
	
	private BoardORM(){
		cache = new Hashtable<Integer,BoardCache>();
		
		ThreadTool.runTask(new Runnable(){
			public void run() {
				while(true){
					ThreadTool.sleep(1000L*60*60);
					
					for(Map.Entry<Integer, BoardCache> kv : cache.entrySet()){
						if(kv.getValue().canRemove()){
							System.out.println("["+new Date(System.currentTimeMillis()).toString()+"]"+
										"remove board cache: cot_id="+kv.getKey());
							cache.remove(kv.getKey());
						}
					}
				}
			}		
		});
	}
	
	public synchronized static BoardORM getInstance(){
		if(instance==null) instance=new BoardORM();
		return instance;
	}
	
	public Board loadByContestId(int cot_id){
		BoardCache bc;
		
		if(cache.containsKey(cot_id)){
			bc = cache.get(cot_id);
			
			if(bc.isNewest()) return bc.getBoard();
			
			if(!bc.tryLock()){
				return bc.getBoard();
			}
			
			if(bc.isNewest()) return bc.getBoard();
		}else{
			synchronized(this){
				if(!cache.containsKey(cot_id)){
					cache.put(cot_id, new BoardCache());
					System.out.println("["+new Date(System.currentTimeMillis()).toString()+"]"+
							"add board cache, cot_id: "+cot_id);
				}
			}
			
			bc = cache.get(cot_id);
			
			if(!bc.tryLock()){
				while(!bc.isNewest()){
					ThreadTool.sleep(wait_limit);
				}
				return bc.getBoard();
			}
		}
		
		String sql = "select smt_time,smt_user,jdg_res,prb_id"+ 
				" from contest_submit,submit_record,judge_result"+
				" where submit_record.smt_id=contest_submit.smt_id"+ 
				" and judge_result.smt_id=contest_submit.smt_id"+
				" and contest_submit.cot_id=?"+
				" and jdg_res!=?"+
				" order by submit_record.smt_time";
		
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);
		ResultSet res=null;
		
		try {
			pst.setInt(1, cot_id);
			pst.setString(2, OnlineJudge.JE);
			res=pst.executeQuery();
			Board board=new Board(ContestORM.getInstance().findById(cot_id));
			board.read(res);
			bc.update(board);		
			return board;		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			bc.unlock();
			con.close();
		}
	}
	
	private class BoardCache{
		private Board board;
		private Date lastFresh;
		private boolean used = false;
		
		public synchronized void update(Board board){
			lastFresh = new Date(System.currentTimeMillis());
			this.board = board;
		}
		
		public synchronized Board getBoard(){
			return this.board;
		}
		
		public synchronized boolean isNewest(){
			if(lastFresh==null) return false;
			long offset = System.currentTimeMillis()-lastFresh.getTime();
			return offset < (1000*15);
		}
		
		public synchronized boolean tryLock(){
			if(used) return false;
			return (used = true);
		}
		
		public void unlock(){
			used = false;
		}		
		
		public boolean canRemove(){
			long offset = System.currentTimeMillis()-lastFresh.getTime();
			long m = offset / (1000*60);
			return m>60;
		}
				
	}
	
}
