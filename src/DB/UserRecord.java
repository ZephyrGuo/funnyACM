package DB;

public class UserRecord {
	private int total_task;
	private int total_problem;
	private int best_rank;
	private int best_score;
	private int total_score;
	
	
	public int getTotal_task() {
		return total_task;
	}
	public void setTotal_task(int total_task) {
		this.total_task = total_task;
	}
	public int getTotal_problem() {
		return total_problem;
	}
	public void setTotal_problem(int total_problem) {
		this.total_problem = total_problem;
	}
	public int getBest_rank() {
		return best_rank;
	}
	public void setBest_rank(int best_rank) {
		this.best_rank = best_rank;
	}
	public int getBest_score() {
		return best_score;
	}
	public void setBest_score(int best_score) {
		this.best_score = best_score;
	}
	public int getTotal_score() {
		return total_score;
	}
	public void setTotal_score(int total_score) {
		this.total_score = total_score;
	}
}
