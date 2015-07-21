<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ 
page import="
base.Task,
DB.TaskInfo,
DB.TaskInfoORM,
event.LoadTaskStatusTask
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
	int task_id;
	try{
	    task_id = Integer.parseInt(request.getParameter("task_id"));
	}catch(Exception e){
		response.sendRedirect("./Error.jsp?msg=Access error");
		return ;
	}
	TaskInfo ti = TaskInfoORM.getInstance().loadById(task_id);
	if(ti==null){
		response.sendRedirect("./Error.jsp?msg=Access error");
		return ;
	}
%>
	<div class="container">
		<div class="page-header">
  			<h2><%=ti.getTask_name()%></h2>
		</div>
		
		<table class="table table-striped">
	 		<tr>
	 			<td>姓名</td>
	 			<td>是否完成任务</td>
	 			<td>题量</td>
	 		</tr>
	 		<tbody id="task_status_table">
		 <%	
	 		Task task = new LoadTaskStatusTask(out);
	 		task.doRequest(request);
	 		task.doResponse(response);
		 %>
	 		</tbody>
 		</table>	
	</div>	

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>