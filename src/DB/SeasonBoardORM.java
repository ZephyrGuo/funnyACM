package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import base.ObjectORM;
import base.PooledConnection;

public class SeasonBoardORM extends ObjectORM {
	
	private static SeasonBoardORM instance;
	
	public synchronized static SeasonBoardORM getInstance(){
		if(instance==null) instance=new SeasonBoardORM();
		return instance;
	}	
	
	public SeasonBoard loadById(int sea_id){
		String sql = "select user.user_name, season_rating.rating, season_rating.join_cnt, user.user_id"+
				" from season_rating, user"+
				" where sea_id=? and user.user_id=season_rating.user_id"+
				" order by rating desc, join_cnt desc";
		PooledConnection con;
		con=this.getConnection();
		PreparedStatement pst=con.getPrepareStatement(sql);

		try {
			pst.setInt(1, sea_id);
			ResultSet res=pst.executeQuery();
			SeasonBoard board=new SeasonBoard();
			board.read(res);
			return board;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			con.close();
		}
	}
}
