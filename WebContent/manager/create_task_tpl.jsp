<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
	common.Configuration,
	java.util.Properties
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
	textarea{
		width:100%;
	}
	
	。__table{
		width:100%;	
	}
	
	.no_point{
		list-style: none;
		padding-left: 0;
	}
	
	.choosed_user_list{
		float:left;
		padding-left: 8px;
	}
	
</style>
</head>

<body>
	  
		 <table class="__table">
			  <tr>
			      <td><label>任务模板名</label></td>
			      <td width="94%"><input type="text" class="form-control" id="task_tpl_name" value=""></td>
			  </tr>
			  <tr>
			      <td><button type="button" class="btn btn-primary" onclick="add_prb_to_page()">添加题目</button></td>
			  </tr>
		 </table> 
		
		<br/>
		
		<table id="problem_set">
			<tr id="first_problem">
			  <td>   
				<div>
				   <select id="oj_name">
	<%
					  Properties conf = Configuration.load("conf/common.properties");
					  String oj_list = conf.getProperty("oj_list");
					  String[] list = oj_list.split(";");
					  for(int i=0;i<list.length;i++){
					  	out.println("<option value=\""+list[i]+"\">"+list[i]+"</option>");
					  }
	%>
					</select>
				  </div>
			  </td>
			  <td>
			   	<input type="number" class="form-control" placeholder="题号" value="">
			  </td>
			</tr>
		</table>
		
		<div align="center">
		
			<button type="button" class="btn btn-success" onclick="create_tpl()">创建</button>
		
		</div>
		
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../dist/js/bootstrap.min.js"></script>
</body>
</html>