package servlet;

import javax.servlet.annotation.WebServlet;

import base.MultiTaskServlet;

/**
 * Servlet implementation class Season
 */
@WebServlet("/Season")
public class Season extends MultiTaskServlet {
	private static final long serialVersionUID = 1L;
       
    public Season() {
        super();
    }

	@Override
	protected void initRegist() {
		this.registTask("apply","ApplySeasonTask");
		this.registTask("calRating", "CalSeasonRatingTask");
	}
}
