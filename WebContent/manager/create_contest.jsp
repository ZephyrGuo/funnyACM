<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
	common.Configuration,
	java.util.Properties,
	java.text.SimpleDateFormat
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
	.no_point{
		list-style: none;
		padding-left: 0;
	}
	
	.choosed_sea_list{
		float:left;
		padding-left: 8px;
	}
	
</style>

</head>

<body>
	
	
	  <div class="form-group">
	    <label for="exampleInputEmail1">比赛名称</label>
	    <input type="text" class="form-control" id="cot_name" placeholder="小于20字">
	  </div>
	  <div class="form-group">
	    <label for="exampleInputPassword1">开始时间<small>（格式如2015-04-07 19:07）</small></label>
	    <input type="text" class="form-control" id="start_time">
	  </div>
	  <div class="form-group">
	    <label for="exampleInputPassword1">结束时间<small>（格式如2015-04-07 19:07）</small></label>
	    <input type="text" class="form-control" id="end_time">
	  </div>


	  
	  <button  class="btn btn-primary" data-toggle="modal" data-target="#chooseSeasonModal">赛季选择</button>
	  <br/>
	  
		<div class="panel panel-default">
  			<div class="panel-heading">[已选赛季]</div>
  			<div class="panel-body">
 		
 			<ul class="no_point" id="choosed_sea_list">
 			
 				<!-- choosed season list -->
 						 				
 			</ul>
 			
  			</div>
		</div>
	  
	  <button  class="btn btn-primary" onclick="add_prb_to_page()">添加题目</button>
	  
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
			<button type="button" class="btn btn-success" onclick="createContest()">创建比赛</button>
		</div>
	
	
<!-- choose season Modal -->
<div class="modal fade" id="chooseSeasonModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
    
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">勾选比赛归属的赛季</h4>
      </div>
      
      <div class="modal-body pre-scrollable">       
        <ul class="list-group" id="season_list">
        
		<!-- season list -->

		</ul>	
      </div>

      <div class="modal-footer">
      		<label>输入赛季名模糊查询：</label>
      		<input type="text" id="sea_name_input" value="">
        	<button class="btn btn-primary" onclick="findSeason()">查询</button>
        	<button class="btn btn-success" onclick="addSeason()">添加</button>
      </div>
      
   </div>
  </div>
</div>
	
	
	
	
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../dist/js/bootstrap.min.js"></script>
</body>
</html>