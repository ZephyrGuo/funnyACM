<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
base.Task,
event.LoadTaskProbListTask,
DB.TaskInfo,
DB.TaskInfoORM
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
<link href="./css/style.css" rel="stylesheet" type="text/css" media="all" />
<script src="./js/jquery.min.js"></script>
<link href="./css/animate.css" rel="stylesheet" type="text/css" media="all">
<link href="./css/funnyACM.css" rel="stylesheet" type="text/css" media="all">
<script src="./js/funnyACM.js"></script>

</head>

<%
	int task_id;
	
	try{
		task_id = Integer.parseInt(request.getParameter("task_id"));
	}catch(Exception e){
		response.sendRedirect("./Error.jsp?msg=Access error");
		return ;
	}
	
	TaskInfo t = TaskInfoORM.getInstance().loadById(task_id);

	if(t==null){
		response.sendRedirect("./Error.jsp?msg=no the task");
		return ;
	}
%>


<body>
	<!----header---->
	<div class="header">
		<div class="container">
			<div class="logo">
				<img src="./images/logo.png">
			</div>
			<div class="top-nav">				
				<h1><%=t.getTask_name()%></h1>
			</div>
		</div>
		
	</div>
	<!----//header---->
	<!-- hr -->
	<div class="modal-footer">
		<button type="button" class="btn btn-info btn-xs" onclick="onClick=history.go(-1)"><span class="glyphicon glyphicon-arrow-left"></span></button>
	</div>

	<div class="container">
		<div class="panel panel-default">
		  <div class="panel-heading">
		   	 <div class="pull-left"><h4>训练题目</h4></div>
		 	 <div align="right"><a class="btn btn-default btn-sm" href="./task_rank.jsp?task_id=<%=task_id%>" target="_blank">训练情况</a></div>  
		  </div>
		  <div class="panel-body">
		    
			<table class="table table-bordered">				
				<%
					Task task=new LoadTaskProbListTask(out);
					task.doRequest(request);
					task.doResponse(response);
				%>
			</table>
		   
		  </div>
		</div>		
		
	</div>
	
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>
