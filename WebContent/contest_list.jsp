<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="

base.Task,
event.LoadCanJoinContestTask,
DB.ContestORM

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
				  <li role="presentation"  class="active"><a href="./contest_list.jsp?i=0&n=15">比赛</a></li>
				  <li role="presentation"><a href="./personal_data.jsp">个人数据</a></li>
				</ul>
				</div>
			</div>
		</div>
		
	</div>
	<!----//header---->
	<!-- hr -->
	<div class="modal-footer"></div>
	
	<div class="container">
		<div>
		<button type="button" class="btn btn-success" onclick="window.open('./apply_season.jsp')">赛季报名与查看</button>
		</div>
		<br/><br/>
		<div class="panel panel-info">
		  <div class="panel-heading"><h4>可参加的比赛</h4></div>
		  <div class="panel-body">
		    
			<div class="list-group" align="center">
<%
		LoadCanJoinContestTask task=new LoadCanJoinContestTask(out);
		task.doRequest(request);
		if(task.getErrno()!=0){
			response.sendRedirect("./Error.jsp?msg=Access error");
			return ;
		}
		task.doResponse(response);
		
%>
			  			  
			</div>		    
		  </div>
		</div>		
	</div>
	

<%
	int i = Integer.valueOf(request.getParameter("i"));
	int n = 15; 
%>

<div class="paging">
<nav>
  <ul class="pager">
    
<%
	out.println(task.getPageBtnHTML());
%>
    
  </ul>
</nav>
</div>	

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>