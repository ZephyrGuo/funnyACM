package event;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import DB.Problem;
import DB.ProblemORM;
import base.Task;

public class LoadSomeProblemTask extends Task {

	List<Problem> list;
	private JspWriter out;
	
	public LoadSomeProblemTask(JspWriter out){
		this.out = out;
	}
	
	@Override
	public void doRequest(HttpServletRequest request) throws IOException {
		int i = Integer.valueOf(request.getParameter("i"));
		int n = Integer.valueOf(request.getParameter("n"));
		
		list = ProblemORM.getInstance().loadProbInfoPaging(i, n);
	}

	@Override
	public void doResponse(HttpServletResponse response) throws IOException {
		
		String HTML = "";
		
		for(Problem p : list){
			HTML += "<tr><td>"+p.getOj_name()+"</td>";
			HTML += "<td>" + p.getOj_id() + "</td>";
			HTML += "<td align=\"center\"><div class=\"btn-group\" role=\"group\" >"
			 + "<a class=\"btn btn-primary\" href=\"../problem.jsp?prb_id="+p.getPrb_id()+"&type=-1\" target=\"_blank\">详细</a>"
			 + "<a class=\"btn btn-success\" href=\"./edit_problem.jsp?prb_id="+p.getPrb_id()+"\">编辑</a>"
			 + "</div></td></tr>"; 
		}

		out.println(HTML);
	}

}
