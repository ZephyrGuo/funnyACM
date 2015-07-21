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


</head>

<body>


<div align="right">
  	<table> 
  	<tr>
	    <td> <label>学号</label> </td>
	    <td> <input type="text" class="form-control" id="user_id" value=""> </td>
	    <td> <button class="btn btn-primary" onclick="queryUserRecord()">查询</button> </td>
  	</tr> 
  	</table>
</div>
  
<br/>


 	<table class="table table-striped">
 		<tr>
 			<td>姓名</td>
 			<td>完成任务数</td>
 			<td>题量</td>
 			<td>赛季最高名次</td>
 			<td>赛季最高得分</td>
 			<td>所有赛季总得分</td>
 		</tr>
 		<tr>
 			<td id="user_name"></td>
 			<td id="total_task"></td>
 			<td id="total_problem"></td>
 			<td id="best_rank"></td>
 			<td id="best_score"></td>
 			<td id="total_score"></td>
 		</tr>
 	</table>


    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../dist/js/bootstrap.min.js"></script>
</body>
</html>