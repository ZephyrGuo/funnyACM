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
				$(window.parent.document).find("#manage_page").height($("body").height());
			}	
		)
</script>

<style>

	td{
		padding:10px;
	}

</style>

</head>

<body>


<div align="right">
  	<table> 
  	<tr>
	    <td> <label>学号</label> </td>
	    <td> <input type="text" class="form-control" id="user_id" value=""> </td>
	    <td> <button class="btn btn-primary" onclick="findUserById()">查询</button> </td>
  	</tr> 
  	</table>
</div>
  
<br/>

<div class="panel panel-default">
  <div class="panel-heading">信息</div>
  <div class="panel-body">
  	<table>
    	    <tr>
    	    	<td> <label>姓名</label> </td>
    	    	<td> <input type="text" class="form-control" id="user_name"> </td>
    	    </tr>
    	    <tr>
    	    	<td> <label>密码</label> </td>
    	    	<td> <input type="text" class="form-control" id="user_psw"> </td>
    	    	<td> 密码置空表示不修改 </td>
    	    </tr>
    	    <tr>
    	    	<td> <label>权限</label> </td>
    	    	<td>
		    	    <select id="user_type">
		    	    	<option value="-1"></option>
		    	    	<option value="0">新生</option>
		    	    	<option value="1">集训队员</option>
		    	    	<option value="2">管理员</option>  	    	
		    	    </select>
	    	    </td>
    	    </tr>
    </table>
  </div>
</div>

	<div align="center">
		 <button class="btn btn-success" onclick="modifyUser()">修改</button>  	
	</div>

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../dist/js/bootstrap.min.js"></script>
</body>
</html>