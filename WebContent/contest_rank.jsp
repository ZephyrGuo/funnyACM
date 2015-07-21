<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ 
page import="
event.LoadBoardTask,
event.LoadContestInfoTask,
base.Task,
java.util.List,
DB.Board,
DB.Contest
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
	Task task=new LoadBoardTask();
	task.doRequest(request);

	switch(((LoadBoardTask)task).getErrno()){
		case 1: response.sendRedirect("./Error.jsp?msg=Access error"); return;
		case 2: response.sendRedirect("./Error.jsp?msg=No ranklist of the contest"); return;
	}
	
	Board board=((LoadBoardTask)task).getBoard();
	List<String>[] table=board.getBoard();
	
	task=new LoadContestInfoTask();
	task.doRequest(request);
	Contest contest=((LoadContestInfoTask)task).getContest();
%>	
	<div class="container">
		<div class="page-header">
  			<h2><%=contest.getCot_name()%></h2>
		</div>
		<table class="table table-hover">
		      <thead>
		        <tr>
		          <th>#</th>
		          <th>姓名</th>
		          <th>题数</th>
		          <th>罚时</th>
		          <%
		          	int n=contest.getPrb_list().length();
		            n = n - n/2;
		          	for(int i=0;i<n;i++){
		          		out.println("<th>"+((char)(i+'A'))+"</th>");
		          	}
		          %>
		        </tr>
		      </thead>
		      <tbody>
		      <%
		      	for(int i=0;i<table.length;i++){
		      		List<String> tr=table[i];
		      		out.println("<tr>");		      		
		      		for(int j=0;j<tr.size();j++){
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