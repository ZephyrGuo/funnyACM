<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="
DB.UserRecord,
DB.UserRecordORM,
DB.UserORM
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
			<div class="top-nav">
				<div class="top-nav-left">
				<ul class="nav nav-pills" role="tablist">
				  <li role="presentation"><a href="./train.jsp?i=0&n=15">训练</a></li>
				  <li role="presentation"><a href="./contest_list.jsp?i=0&n=15">比赛</a></li>
				  <li role="presentation" class="active"><a href="./personal_data.jsp">个人数据</a></li>
				</ul>
				</div>
			</div>
		</div>
		
	</div>
	<!----//header---->
	<!-- hr -->
	<div class="modal-footer"></div>
	
<%
	String user_id = (String)request.getSession().getAttribute("handle");
	UserRecord r = UserRecordORM.getInstance().loadById(user_id);
	
%>	
	
	<div class="container" align="center">
		
		<div>
			<h2><strong><%=UserORM.getInstance().loadById(user_id).getUser_name()%></strong></h2>
		</div>
		
		<br/>
		
		<div>
			<h1 class="glyphicon glyphicon-pencil" aria-hidden="true"></h1>
			<h3>至今共完成了<font color="#d9534f"><%=r.getTotal_task()%></font>个任务</h3>
			<h3>至今共训练了<font color="#d9534f"><%=r.getTotal_problem()%></font>道题</h3>
		</div>
				
		<div>
			<h1 class="glyphicon glyphicon-stats" aria-hidden="true"></h1>
			<h3>最高获得了赛季中的第<font color="#d9534f"><%=r.getBest_rank()%></font>名</h3>
			<h3>最高赛季得分<font color="#d9534f"><%=r.getBest_score()%></font></h3>
			<h3>至今所有赛季总得分<font color="#d9534f"><%=r.getTotal_score()%></font></h3>		
		</div>
			
	</div>
	

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>