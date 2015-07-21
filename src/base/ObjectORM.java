package base;

import java.sql.SQLException;

import base.PooledConnection;

public class ObjectORM {
	
	private String port;
	private String ip;
	private String account;
	private String password;
		
	protected ObjectORM(){

	}
	
	protected PooledConnection getConnection(){
		PooledConnection con = null;
		
		try {
			con = ConnectionPool.getInstance().getConnection();
			if(con==null){
				ConnectionPool.getInstance().createPool();
				con = ConnectionPool.getInstance().getConnection();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}

}
