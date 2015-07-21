<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
	DB.ProblemORM,
	DB.Problem
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
	
	.__table{
		width:100%;	
	}
</style>


</head>

<body>

<%
	int prb_id = Integer.parseInt(request.getParameter("prb_id"));	
	Problem prb = ProblemORM.getInstance().LoadDetailByPrbId(prb_id);
%>
		  
	<table class="__table">
		  <tr>
		  	 <td><label>标题</label></td>
		  	 <td width="94%"><input type="text" class="form-control" id="title" value="<%=prb.getTitle()%>"></td>
		  </tr>
		  
		  <tr>
		  	 <td><label>题目描述</label></td>
		  	 <td><textarea id="problemDetail" rows="10"><%=prb.getProblemDetail()%></textarea></td>
		  </tr>
		  
		  <tr>
		  	 <td><label>输入描述</label></td>
		  	 <td><textarea id="inputDetail" rows="5"><%=prb.getInputDetail()%></textarea></td>
		  </tr>
		  
		  <tr>
		  	 <td><label>输出描述</label></td>
		  	 <td><textarea id="outputDetail" rows="5"><%=prb.getOutputDetail()%></textarea></td>
		  </tr>	
		  
		  <tr>
		  	 <td><label>输入样例</label></td>
		  	 <td><textarea id="sampleInput" rows="5"><%=prb.getSampleInput() %></textarea></td>
		  </tr>	
		  
		  <tr>
		  	 <td><label>输出样例</label></td>
		  	 <td><textarea id="sampleOutput" rows="5"><%=prb.getSampleOutput()%></textarea></td>
		  </tr>	

		  <tr>
		  	 <td><label>提示</label></td>
		  	 <td><textarea id="hintDetail" rows="5"><%=prb.getHintDetail()%></textarea></td>
		  </tr>
		  			  		  	  
	</table>
					
	  	<div align="center">
	  		<br/>
	  		<button type="button" class="btn btn-success" onclick="updProblem()">更新</button>
	  	</div>
	 	 	
	 	<input type="hidden" id="prb_id" value="<%=prb_id%>"/>
</body>
</html>