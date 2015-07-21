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
	
	.__table{
		width:100%;	
	}
	
	
</style>


</head>

<body>
	
		
		<table>
		  <tr>
		  <td> <label>OJ</label> </td>
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
		  </tr>
		   
		  <tr>
		      <td><label>题目题号</label></td>
		      <td><input type="number" class="form-control" id="oj_id" placeholder="题号" value=""></td>
		      <td><button type="button" class="btn btn-primary" onclick="addProblem()">抓取</button></td>		 
		  </tr>
		  
		  <tr>		
		    <td></td>  	
		  	<td align="right">根据网络情况不同，抓取之后请耐心等待数秒（尤其是POJ）</td>
		  	<td></td>
		  </tr>
		  
		  </table>
		  
		  <hr/>
		  
		  <table class="__table">
		  <tr>
		  	 <td><label>标题</label></td>
		  	 <td width="94%"><input type="text" class="form-control" id="title" value=""></td>
		  </tr>
		  
		  <tr>
		  	 <td><label>题目描述</label></td>
		  	 <td><textarea id="problemDetail"  rows="10"></textarea></td>
		  </tr>
		  
		  <tr>
		  	 <td><label>输入描述</label></td>
		  	 <td><textarea id="inputDetail" rows="5"></textarea></td>
		  </tr>
		  
		  <tr>
		  	 <td><label>输出描述</label></td>
		  	 <td><textarea id="outputDetail" rows="5"></textarea></td>
		  </tr>	
		  
		  <tr>
		  	 <td><label>输入样例</label></td>
		  	 <td><textarea id="sampleInput" rows="5"></textarea></td>
		  </tr>	
		  
		  <tr>
		  	 <td><label>输出样例</label></td>
		  	 <td><textarea id="sampleOutput" rows="5"></textarea></td>
		  </tr>	

		  <tr>
		  	 <td><label>提示</label></td>
		  	 <td><textarea id="hintDetail" rows="5"></textarea></td>
		  </tr>
		  			  		  	  
		</table>
					
	  	<div align="center">
	  		<br/>
	  		<button type="button" class="btn btn-success" onclick="saveProblem()">加入题库</button>
	  	</div>
	 	
	 	<input type="hidden" id="timeLimit" value=""> 
	 	<input type="hidden" id="memoryLimit" value=""> 	
	 	<input type="hidden" id="submit_params" value=""> 
	 	 	
	
</body>
</html>