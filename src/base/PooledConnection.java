package base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * 内部使用的用于保存连接池中连接对象的类
 * 
 * 此类中有两个成员，一个是数据库的连接，另一个是指示此连接是否
 * 
 * 正在使用的标志。
 */

public class PooledConnection {

	private Connection connection = null;// 数据库连接

	private boolean busy ; // 此连接是否正在使用的标志，默认没有正在使用

	// 构造函数，根据一个 Connection 构告一个 PooledConnection 对象

	public PooledConnection(Connection connection) {

		this.connection = connection;

	}


	// 返回此对象中的连接

	protected Connection getConnection() {

		return connection;

	}

	public PreparedStatement getPrepareStatement(String sql){
		try {
			return connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 设置此对象的，连接

	protected void setConnection(Connection connection) {

		this.connection = connection;

	}

	// 获得对象连接是否忙

	protected synchronized boolean isBusy() {
		return busy;
	}

	// 设置对象的连接状态

	protected synchronized void setBusy(boolean busy) {
		this.busy = busy;

	}
	
	public void setAutoCommit(boolean b) throws SQLException{
		this.connection.setAutoCommit(b);
	}
	
	public void commit() throws SQLException{
		this.connection.commit();
	}
	
	public void rollback(){
		try {
			this.connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		busy = false;
	}

}