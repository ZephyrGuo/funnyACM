<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ 
page import="
DB.Problem,
event.LoadProblemTask,
event.LoadSubmitRecordTask,
base.Task
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
<link href="./dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="icon" href="./images/zucc.jpg">
<link href="./css/style.css" rel="stylesheet" type="text/css"
	media="all" />
<script src="./js/jquery.min.js"></script>
<link href="./css/animate.css" rel="stylesheet" type="text/css"
	media="all">
<link href="./css/funnyACM.css" rel="stylesheet" type="text/css"
	media="all">
<script src="./js/funnyACM.js"></script>

</head>
<body>
	<!----header---->
	<div class="header">
		<div class="container">
			<div class="logo">
				<img src="./images/logo.png">
			</div>
		</div>

	</div>
	<!----//header---->
	<!-- hr -->
	<div class="modal-footer"></div>
<%
	Task task=new LoadProblemTask();
	task.doRequest(request);
	Problem prb=((LoadProblemTask)task).getProblem();
	if(prb==null){
		response.sendRedirect("./Error.jsp?msg=Access error");
		return ;
	}
%>
	<div class="container">
		
		<div class="problem_describe">
			<div class="page-header">
  				<h2><%=prb.getTitle()%>&nbsp;&nbsp;<small><%="时限："+prb.getTimeLimit()+" 内存限制："+prb.getMemoryLimit()%></small></h2>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">题目描述</div>

				<div class="panel-body"><%=prb.getProblemDetail()%></div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">输入描述</div>
				<div class="panel-body"><%=prb.getInputDetail()%></div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">输出描述</div>
				<div class="panel-body"><%=prb.getOutputDetail()%></div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">样例输入</div>
				<div class="panel-body"><%=prb.getSampleInput()%></div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">样例输出</div>
				<div class="panel-body"><%=prb.getSampleOutput()%></div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">提示</div>
				<div class="panel-body"><%=prb.getHintDetail()%></div>
			</div>
			<br>
			<% 
				if(!request.getParameter("type").equals("-1"))
					out.println("<button type=\"button\" class=\"btn btn-primary\" data-target=\"#myModal\" data-toggle=\"modal\">提交代码</button>");
			%>
			<br> <br>
		</div>
<%
    task=new LoadSubmitRecordTask(out);
	task.doRequest(request);
	if(((LoadSubmitRecordTask)task).getErrno()!=0){
		response.sendRedirect("./Error.jsp?msg=Access error");
		return ;
	}
%>
		<div class="submit_record">
			<div class="list-group">
				<a class="list-group-item active"> 提交记录 </a>
				<%
					task.doResponse(response);			
				%>
			</div>
		</div>
		
	</div>
	
	<!-- submit Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">代码提交</h4>
	      </div>    
	      <div class="modal-body">
		    <select name="language" id="language">
	    		<option value="c++">C++
	    		</option>
	    		<option value="java">Java
	    		</option>
	    	</select>
	    	<br/><br/>
		    <textarea cols="74" rows="20" name="code" id="code"></textarea>
	      </div>
	      <div class="modal-footer">
	        	<button class="btn btn-primary" onclick="submit(<%=request.getParameter("prb_id")%>,<%=request.getParameter("undetermined_id")%>,<%=request.getParameter("type")%>,<%=request.getParameter("prb_idx")%>)" data-dismiss="modal">提交</button>
	      </div>
	    </div>
	  </div>
	</div>

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>