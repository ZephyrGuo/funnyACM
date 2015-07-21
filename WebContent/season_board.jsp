<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ 
page import="
event.LoadSeasonBoardTask,
event.LoadSeasonTask,
base.Task,
java.util.List,
DB.SeasonBoard,
DB.Season
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
	Task task=new LoadSeasonBoardTask();
	task.doRequest(request);
	SeasonBoard board=((LoadSeasonBoardTask)task).getSeasonBoard();
	List<String>[] table=board.convertSeasonBoard();
	
	task=new LoadSeasonTask();
	task.doRequest(request);
	Season sea=((LoadSeasonTask)task).getSeason();	
%>	
	<div class="container">
		<div class="page-header">
  			<h2><%=sea.getSea_name()%></h2>
		</div>
		<table class="table table-hover">
		      <thead>
		        <tr>
		          <th>#</th>
		          <th>姓名</th>
		          <th>分数</th>
		          <th>参赛次数</th>
		        </tr>
		      </thead>
		      <tbody>
		      <%
		      	for(int i=0;i<table.length;i++){
		      		List<String> tr=table[i];
		      		out.println("<tr>");		      		
		      		for(int j=0;j<4;j++){
		      			out.println("<td>"+tr.get(j)+"</td>");
		      		}
		      		out.println("</tr>");
		      	}
		      %>
		      </tbody>
		 </table>
	</div>	

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>