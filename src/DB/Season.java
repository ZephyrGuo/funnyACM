package DB;

import java.sql.Timestamp;

public class Season {
	private int sea_id;
	private String sea_des;
	private String sea_name;
	private int sea_len;
	private Timestamp sea_create;
	
	
	
	public int getSea_id() {
		return sea_id;
	}
	public void setSea_id(int sea_id) {
		this.sea_id = sea_id;
	}
	public String getSea_des() {
		return sea_des;
	}
	public void setSea_des(String sea_des) {
		this.sea_des = sea_des;
	}
	public String getSea_name() {
		return sea_name;
	}
	public void setSea_name(String sea_name) {
		this.sea_name = sea_name;
	}
	public int getSea_len() {
		return sea_len;
	}
	public void setSea_len(int sea_len) {
		this.sea_len = sea_len;
	}
	public Timestamp getSea_create() {
		return sea_create;
	}
	public void setSea_create(Timestamp sea_create) {
		this.sea_create = sea_create;
	}
	
	
}
