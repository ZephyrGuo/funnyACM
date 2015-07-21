<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
	common.Configuration,
	java.util.Properties,
	base.Task,
	event.LoadTaskStatusTask,
	DB.TaskInfo,
	DB.TaskInfoORM,
	DB.TaskTemplate,
	DB.TaskTemplateORM,
	DB.User,
	DB.UserORM,
	java.text.SimpleDateFormat,
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
	textarea{
		width:100%;
	}
	
	.__table{
		width:100%;	
	}
	
	.no_point{
		list-style: none;
		padding-left: 0;
	}
	
	.Left{
		float:left;
		padding-left: 8px;
	}
	
</style>

</head>

<%
	int task_id = Integer.valueOf(request.getParameter("task_id"));
	TaskInfo t = TaskInfoORM.getInstance().loadById(task_id);
	
	TaskTemplate tp = TaskTemplateORM.getInstance().loadById(t.getTask_tpl_id());
	
	String[] strs = tp.getPrb_list().split(";");
	Problem[] prbs = new Problem[strs.length];
	
	for(int i=0;i<strs.length;i++){
		prbs[i] = ProblemORM.getInstance().LoadInfoByPrbId(Integer.valueOf(strs[i]));
	}
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
%>

<body>
	 	<input type="hidden" id="task_id" value="<%=t.getTask_id()%>"> 
		 <table class="__table">
			  <tr>
			      <td width="10%"><label>任务名</label></td>
			      <td width="80%"><input type="text" class="form-control" id="task_name" value="<%=t.getTask_name()%>"></td>
			  </tr>
			  <tr>
			  	 <td><label>任务描述</label></td>
			  	 <td><textarea id="task_description" rows="5"><%=t.getTask_description()%></textarea></td>
			  </tr>
			  <tr>
			      <td><label>开始日期</label></td>
			      <td><input type="text" class="form-control" id="start_time" value="<%=fmt.format(t.getStart_time())%>">格式如：2015-04-07 18:05</td>
			  </tr>
			  <tr>
			  	 <td><label>任务模板 </label></td>
			  	 <td><button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#chooseTplModal">修改</button></td>
			  </tr>
			  <tr>
			  	 <td><label>队员 </label></td>
			  	 <td><button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#chooseUserModal">新增</button></td>
			  </tr>
		</table>
					
	 	<br/>
	 	
		<div class="panel panel-default">
  			<div class="panel-heading">[新增队员]</div>
  			<div class="panel-body">
 		
 			<ul class="no_point" id="choosed_user_list">
 			
 				<!-- choosed user list -->
		 				
 			</ul>
 			
  			</div>
		</div>	 
			
		<div class="panel panel-default">
  			<div class="panel-heading">[任务完成条件设置]</div>
  			<div class="panel-body">
 			
 			<table>
 			
	 			<tr><td>
	 				<label>至少需要完成的题数:</label>
					<input type="number" class="form-control" id="condition_cnt" value="<%=t.getCondition_mask()>>26%>">
				</td></tr>
	 			
	 			<tr><td><br/><label>必做题勾选:</label></td></tr>
	 			
	 			<tr><td>
					<ul class="no_point" id="check_box_set">
<%
				for(int i=0;i<prbs.length;i++){
					if((t.getCondition_mask() & (1<<i))!=0){
						out.println("<li><label class=\"checkbox-inline\"><input type=\"checkbox\" checked=\"checked\">"+
							prbs[i].getOj_name()+"."+prbs[i].getOj_id()+"</label></li>");
					}else{
						out.println("<li><label class=\"checkbox-inline\"><input type=\"checkbox\">"+
								prbs[i].getOj_name()+"."+prbs[i].getOj_id()+"</label></li>");
					}
				}

%>
						<!-- problem set -->
					</ul>
				</td></tr>
								
			</table>		
  			</div>
  			
		</div>
		
		<div align="center">
			<button type="button" class="btn btn-success" onclick="editTask()">修改任务</button>
			<input type="hidden" id="task_tpl_id" value="<%=t.getTask_tpl_id()%>">
		</div>
		
		
<!-- choose task template Modal -->
<div class="modal fade bs-example-modal-lg" id="chooseTplModal" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
    
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="tplModal_label">双击选择模版</h4>
      </div>    
      
      <div class="modal-body pre-scrollable">	     
		<div class="list-group" id="tpl_list">
		
		<!-- task template list -->
	
		</div>	     
      </div>
      
      <div class="modal-footer">
      		<label>输入模版名字模糊查询：</label>
      		<input type="text" id="tpl_name_input" value="">
        	<button class="btn btn-primary" onclick="findTaskTemplate()">查询</button>
      </div>
      
    </div>
  </div>
</div>



<!-- choose user Modal -->
<div class="modal fade" id="chooseUserModal" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
    
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="usrModal_label">选择需要添加的队员</h4>
      </div>
      
      <div class="modal-body pre-scrollable">       
        <ul class="list-group" id="user_list">
        
		<!-- user list -->

		</ul>	
      </div>

      <div class="modal-footer">
      		<label>输入用户名模糊查询：</label>
      		<input type="text" id="user_name_input" value="">
        	<button class="btn btn-primary" onclick="findUser()">查询</button>
        	<button class="btn btn-success" onclick="addUser()">添加</button>
      </div>
      
   </div>
  </div>
</div>


    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../dist/js/bootstrap.min.js"></script>
</body>
</html>