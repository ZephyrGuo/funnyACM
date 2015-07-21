<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
	base.Task,
	event.LoadSomeProblemTask,
	DB.ProblemORM
"
%>

<!DOCTYPE html>
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

<style>
	.paging{
		text-align:center;
		width:100%;
	}
</style>

</head>

<body>
	
	<div align="right">
	  	<table> 
	  	<tr>
		    <td><label>题目</label></td>
		    <td> <input type="text" class="form-control" id="problem" value=""> </td>
		    <td> 
		    	<button class="btn btn-primary" onclick="queryProblem()">查询</button> 
		    </td>
	  	</tr>
	  	</table>
	  	<p>查询格式如 ZOJ.1001</p>
	</div>
	
	<br/>
	
	<%
		Task task = new LoadSomeProblemTask(out);
		task.doRequest(request);
	%>
	
	<table class="table table-bordered">
  		<tr>
  			<td>OJ</td>
  			<td>题号</td>
  			<td align="center"></td>			
  		</tr>
  	<%
  		task.doResponse(response);
  	%>
  		
	</table>

<%
	int i = Integer.valueOf(request.getParameter("i"));
	int n = 15; 
%>


<div class="paging">
<nav>
  <ul class="pager">
  
<%
	String page_btn_HTML = "";

	if(i>0) 
		page_btn_HTML+="<li><a href=\"./problem_set.jsp?i="+(i-n)+"&n="+n+"\">上一页</a></li>";
	if(i+n<ProblemORM.getInstance().countProblem()) 
		page_btn_HTML+="<li><a href=\"./problem_set.jsp?i="+(i+n)+"&n="+n+"\">下一页</a></li>";
	
	out.println(page_btn_HTML);
%>
    
  </ul>
</nav>
</div>

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../dist/js/bootstrap.min.js"></script>
</body>
</html>