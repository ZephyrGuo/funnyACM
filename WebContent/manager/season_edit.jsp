<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
	common.Configuration,
	java.util.Properties,
	DB.Season,
	DB.SeasonORM
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
</style>

</head>
<body>

<%
	int sea_id = Integer.valueOf(request.getParameter("sea_id")); 
	Season s = SeasonORM.getInstance().findbyid(sea_id);	
	
%>

	  <div class="form-group">
	    <label>赛季名称</label>
	    <input type="text" class="form-control" id="sea_name" placeholder="小于20字" value="<%=s.getSea_name()%>">
	  </div>
	  <div class="form-group">
	    <label>赛季时长&nbsp;<small>(单位:天  , 开始时间为今天)</small></label>
	    <input type="number" class="form-control" id="sea_duration" value="<%=s.getSea_len()%>">
	  </div>
	  <div class="form-group">
	    <label>描述</label>
	    <textarea id="sea_desc" rows="5"><%=s.getSea_des()%></textarea>
	  </div>
	 
	 <input type="hidden" id="sea_id" value="<%=s.getSea_id()%>">

	  <div align="center">
		<button type="button" class="btn btn-success" onclick="editSeason()">修改</button>
	  </div>
	

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../dist/js/bootstrap.min.js"></script>
</body>
</html>