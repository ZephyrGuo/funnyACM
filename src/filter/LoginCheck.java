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
 * Servlet Filter implementation class LoginCheck
 */
@WebFilter({"/LoginCheck", "/Type1Check", "/Type2Check", "*.jsp"})
public class LoginCheck implements Filter {

    public LoginCheck() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rsp = (HttpServletResponse)response;
				
		if(req.getSession().getAttribute("handle")==null){
			
			if(!(req.getRequestURL().toString().matches(".*index\\.jsp") ||
					req.getRequestURL().toString().matches(".*Error\\.jsp"))){
				
				rsp.sendRedirect("./index.jsp");
				return ;
				
			}
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
