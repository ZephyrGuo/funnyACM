<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
	DB.TaskTemplate,
	DB.TaskTemplateORM,
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
		
<%
	int task_tpl_id = Integer.valueOf(request.getParameter("task_tpl_id"));
	TaskTemplate t = TaskTemplateORM.getInstance().loadById(task_tpl_id);
	
	String[] prb_list = t.getPrb_list().split(";");
	
	String prb = "";
	
    for(int i=0;i<prb_list.length;i++){
    	Problem p = ProblemORM.getInstance().LoadInfoByPrbId(Integer.valueOf(prb_list[i]));	
    	
    	if(i!=0) prb += ";";
    	prb += p.getOj_name()+"."+p.getOj_id();
    	
    }
	
%>

</head>

<body>
	
	  <div class="form-group">
	    <label for="exampleInputEmail1">任务模版名称</label>
	    <input type="text" class="form-control" id="task_tpl_name" placeholder="小于20字" value="<%=t.getTask_tpl_name()%>">
	  </div>

	  <div class="form-group">
	    <label for="exampleInputPassword1">题目列表&nbsp;&nbsp;<small>格式如[ZOJ.1001] 每题之间用';'隔开 ,OJ名字全大写</small></label>
	    <input type="text" class="form-control" id="prb_list" value="<%=prb%>">
	  </div>
		
	  <div align="center">
	  	<button type="button" class="btn btn-default" onclick="editTaskTemplate()">修改</button>
	  </div>
	  
	  <input type="hidden" id="task_tpl_id" value="<%=t.getTask_tpl_id()%>">
	  
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../dist/js/bootstrap.min.js"></script>
</body>
</html>