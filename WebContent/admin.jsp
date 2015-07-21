<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>funnyACM</title>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap -->
<link href="./dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="icon" href="./images/zucc.jpg">
<link href="./css/style.css" rel="stylesheet" type="text/css" media="all" />
<script src="./js/jquery.js"></script>
<link href="./css/funnyACM.css" rel="stylesheet" type="text/css" media="all">
<link href="./css/admin.css" rel="stylesheet" type="text/css" media="all">
<script src="./js/funnyACM.js"></script>

</head>
<body>
	<!----header---->
	<div class="header">
		<div class="container">
			<div class="logo">
				<a><img src="./images/logo.png"></a>
			</div>
		</div>		
	</div>
	<!----//header---->
	<!-- hr -->
	<div class="modal-footer"></div>
	
	<div class="container">		
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <ul id="main-nav" class="nav nav-tabs nav-stacked" style="">
                    <li class="active">
                        <a href="#">
                            <i class="glyphicon glyphicon-th-large"></i>         
                        </a>
                    </li>
                    <li>
                        <a href="#systemSetting" class="nav-header collapsed" data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i>系统管理
                               <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="systemSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="./manager/user_manager.jsp" target="showframe"><i class="glyphicon glyphicon-user"></i>用户管理</a></li>
                        </ul>
                    </li>
                   	<li>
                        <a href="#taskSetting" class="nav-header collapsed" data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i>任务管理
                               <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="taskSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="./manager/assign_task.jsp" target="showframe"><i class="glyphicon glyphicon-edit"></i>布置任务</a></li>
                            <li><a href="./manager/task_list.jsp?i=0&n=15" target="showframe"><i class="glyphicon glyphicon-list"></i>任务列表</a></li>
                            <li><a href="./manager/create_task_tpl.jsp" target="showframe"><i class="glyphicon glyphicon-file"></i>创建任务模板</a></li>
                            <li><a href="./manager/task_tpl_list.jsp?i=0&n=15" target="showframe"><i class="glyphicon glyphicon-list-alt"></i>任务模板列表</a></li>
                        </ul>
                    </li> 
                   	<li>
                        <a href="#contestSetting" class="nav-header collapsed" data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i>比赛管理
                               <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="contestSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href ="./manager/create_contest.jsp" target ="showframe"><i class="glyphicon glyphicon-plus"></i>创建比赛</a></li>
                            <li><a href="./manager/manager_contest_list.jsp?i=0&n=15" target ="showframe"><i class="glyphicon glyphicon-list"></i>比赛列表</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#seasonSetting" class="nav-header collapsed" data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i>赛季管理
                               <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="seasonSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="./manager/create_season.jsp" target="showframe"><i class="glyphicon glyphicon-stats"></i>创建赛季</a></li>
                            <li><a href="./manager/season_list.jsp" target="showframe"><i class="glyphicon glyphicon-list"></i>赛季列表</a></li>
                            <li><a href="./manager/approve_season.jsp" target="showframe"><i class="glyphicon glyphicon-pencil"></i>申请审批</a></li>                                                      
                        </ul>
                    </li>
                    <li>
                        <a href="#newbieSetting" class="nav-header collapsed" data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i>闯关管理
                               <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="newbieSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="./manager/configurate_section.jsp" target="showframe"><i class="glyphicon glyphicon-wrench"></i>配置关卡题目</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#problemSetting" class="nav-header collapsed" data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i>题库管理
                               <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="problemSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="./manager/capture_problem.jsp" target="showframe"><i class="glyphicon glyphicon-transfer"></i>抓取题目</a></li>
                            <li><a href="./manager/problem_set.jsp?i=0&n=15" target="showframe"><i class="glyphicon glyphicon-book"></i>题库</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#dataStatistics" class="nav-header collapsed" data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i>数据统计
                               <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="dataStatistics" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="./manager/personal_statistics.jsp" target="showframe"><i class="glyphicon glyphicon-floppy-disk"></i>个人数据</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="col-md-10">                             		
			  <div class="panel panel-default"> 
		  		<div class="panel-body"> 
		   		 <iframe id="manage_page" frameborder="0" scrolling="yes"  width="100%" height=100%  name="showframe" src="./manager/welcome.jsp"></iframe>
		  		</div> 
			  </div> 
		
            </div>
        </div>
    </div>

			

		
	</div>
	

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>