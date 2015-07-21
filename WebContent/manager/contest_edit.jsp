<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
	common.Configuration,
	java.util.Properties,
	DB.Contest,
	DB.ContestORM,
	java.text.SimpleDateFormat,
	DB.Season,
	DB.SeasonORM,
	DB.Problem,
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

<%
	int cot_id = Integer.valueOf(request.getParameter("cot_id"));
	Contest c = ContestORM.getInstance().findById(cot_id);
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	String start_time = fmt.format(c.getStart_time());
	String end_time = fmt.format(c.getEnd_time());
%>
	
	  <div class="form-group">
	    <label for="exampleInputEmail1">比赛名称</label>
	    <input type="text" class="form-control" id="cot_name" placeholder="小于20字" value="<%=c.getCot_name()%>">
	  </div>
	  <div class="form-group">
	    <label for="exampleInputPassword1">开始时间<small>（格式如2015-04-07 19:07）</small></label>
	    <input type="text" class="form-control" id="start_time" value="<%=start_time%>">
	  </div>
	  <div class="form-group">
	    <label for="exampleInputPassword1">结束时间<small>（格式如2015-04-07 19:07）</small></label>
	    <input type="text" class="form-control" id="end_time" value="<%=end_time%>">
	  </div>

	  <input type="hidden" id="cot_id" value="<%=c.getCot_id()%>">
	  
	  <button  class="btn btn-primary" data-toggle="modal" data-target="#chooseSeasonModal">赛季选择</button>
	  <br/>
		<div class="panel panel-default">
  			<div class="panel-heading">[已选赛季]</div>
  			<div class="panel-body">
 		
 			<ul class="no_point" id="choosed_sea_list">
 			
 				<!-- choosed season list -->
 <%
 
 			String[] sea_list = c.getSeason_list().split(";");
 			
 			String HTML = "";
 
 			for(int i=0;i<sea_list.length;i++){
 				Season s = SeasonORM.getInstance().findbyid(Integer.valueOf(sea_list[i]));

				HTML += "<li class=\"choosed_sea_list\">";
				HTML += "<h5>["+s.getSea_id()+"]"+s.getSea_name(); 
				HTML += "<button type=\"button\" class=\"btn btn-danger btn-xs\" aria-label=\"Left Align\" onclick=\"removeSeason(this)\">";
				HTML += "<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>";
				HTML += "</button></h5></li>";					
 			}
 			
 			out.println(HTML);
 %>						 				
 			</ul>
 			
  			</div>
		</div>
<%

		String[] prb_list = c.getPrb_list().split(";");
		
		String prb = "";
		
		for(int i=0;i<prb_list.length;i++){
			Problem p = ProblemORM.getInstance().LoadInfoByPrbId(Integer.valueOf(prb_list[i]));	
			
			if(i!=0) prb += ";";
			prb += p.getOj_name()+"."+p.getOj_id();
			
		}

%>	  
		<div class="form-group">
	    <label for="exampleInputPassword1">题目列表&nbsp;&nbsp;<small>格式如[ZOJ.1001] 每题之间用';'隔开 ,OJ名字全大写</small></label>
	    <input type="text" class="form-control" id="prb_list" value="<%=prb%>">
	  	</div>
	
		<div align="center">
			<button type="button" class="btn btn-success" onclick="editContest()">修改</button>
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