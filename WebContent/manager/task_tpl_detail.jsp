<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>

<%@ page import="
	DB.TaskTemplate,
	DB.TaskTemplateORM,
	DB.Problem,
	DB.ProblemORM
"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>funnyACM</title>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap -->
<link href="../dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="icon" href="../images/zucc.jpg">
<link href="../css/style.css" rel="stylesheet" type="text/css" media="all" />
<script src="../js/jquery.min.js"></script>
<link href="../css/animate.css" rel="stylesheet" type="text/css" media="all">
<link href="../css/funnyACM.css" rel="stylesheet" type="text/css" media="all">
<script src="../js/funnyACM.js"></script>


<script language="JavaScript" >
		$(document).ready(
			function(){
				$(window.parent.document).find("#manage_page").height($("body").height()+20);
			}	
		)
</script>

</head>
<body>

<%
	int task_tpl_id = Integer.valueOf(request.getParameter("task_tpl_id"));
	TaskTemplate t = TaskTemplateORM.getInstance().loadById(task_tpl_id);
	String [] prb_list = t.getPrb_list().split(";");
%>
	<table class="table table-striped">
		<tr>
			<td>OJ</td>
			<td>Ã‚∫≈</td>
		</tr>
		
<%
	    for(int i=0;i<prb_list.length;i++){
	    	Problem p = ProblemORM.getInstance().LoadInfoByPrbId(Integer.valueOf(prb_list[i]));	
	    	out.println("<tr>");
	    	out.println("<td>"+p.getOj_name()+"</td>");
	    	out.println("<td>"+p.getOj_id()+"</td>");
	    	out.println("</tr>");
	    }
%>		
		
		
	</table>


</body>
</html>