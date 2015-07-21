package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class Type1Check
 */
@WebFilter({ 
	"/Type1Check",
	"/apply_season.jsp",
	"/code_page.jsp",
	"/contest_list.jsp",
	"/contest_page.jsp",
	"/contest_rank.jsp",
	"/personal_data.jsp",
	"/season_board.jsp",
	"/train.jsp",
	"/training_page.jsp"
})
public class Type1Check implements Filter {

    public Type1Check() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rsp = (HttpServletResponse)response;

		int type=(int)req.getSession().getAttribute("type");
		
		if(type<1){
			rsp.sendRedirect("./Error.jsp?msg=Permission denied");
			return ;
		}
	
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
