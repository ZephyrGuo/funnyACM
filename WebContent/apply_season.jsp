<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ 
page import="
base.Task,
event.LoadApplySeasonTask
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

<style type="text/css">
td{
	padding:10px;
}

div a{
	padding:5px;
	margin:5px;
}

table{
	width: 100%;
}

</style>

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
	
	<div class="container">
		
		<div align="left">
			
			
			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
			<p>
			1.申请赛季之后需要通过管理员的批准 
			</p>
			<p>
			2.只能参与已经通过批准的赛季里的比赛 
			</p>
			<p>
			3.比赛的得分会被记录到赛季榜单里，点击已通过申请的赛季可以查看榜单
			</p>
		</div>
		
		<%
			Task task=new LoadApplySeasonTask(out);	
			task.doRequest(request);
			task.doResponse(response);
		%>
	
	</div>
	
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>
