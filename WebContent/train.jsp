<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="
event.LoadTaskListTask,
base.Task,
DB.UserDoTaskORM
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
<link href="./dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="icon" href="./images/zucc.jpg">
<link href="./css/style.css" rel="stylesheet" type="text/css" media="all" />
<script src="./js/jquery.min.js"></script>
<link href="./css/animate.css" rel="stylesheet" type="text/css" media="all">
<link href="./css/funnyACM.css" rel="stylesheet" type="text/css" media="all">
<link href="./css/timeline.css" rel="stylesheet" type="text/css" media="all">

<script src="./js/funnyACM.js"></script>

</head>
<body>
	<!----header---->
	<div class="header">
		<div class="container">
			<div class="logo">
				<img src="./images/logo.png">
			</div>
			<div class="top-nav">
				<div class="top-nav-left">
				<ul class="nav nav-pills" role="tablist">
				  <li role="presentation" class="active"><a href="./train.jsp?i=0&n=15">训练</a></li>
				  <li role="presentation"><a href="./contest_list.jsp?i=0&n=15">比赛</a></li>
				  <li role="presentation"><a href="./personal_data.jsp">个人数据</a></li>
				</ul>
				</div>
			</div>
		</div>
		
	</div>
	<!----//header---->
	<div class="modal-footer"></div>
	
	<div class="container">
		<ul class="cbp_tmtimeline">
               
               <%
             	    LoadTaskListTask task = new LoadTaskListTask(out);
               		task.doRequest(request);
               		if(task.getErrno()!=0){
               			response.sendRedirect("./Error.jsp?msg=Access error");
               			return ;
               		}
               		task.doResponse(response);
               %>  
                                       
        </ul>
	</div>
	

<%
	int i = Integer.valueOf(request.getParameter("i"));
	int n = 15; 
%>

<div class="paging">
<nav>
  <ul class="pager">
  
<%
	String user_id = (String)request.getSession().getAttribute("handle");
	String page_btn_HTML = "";

	if(i>0) 
		page_btn_HTML+="<li><a href=\"./train.jsp?i="+(i-n)+"&n="+n+"\">上一页</a></li>";
	if(i+n<UserDoTaskORM.getInstance().countUserDoTask(user_id)) 
		page_btn_HTML+="<li><a href=\"./train.jsp?i="+(i+n)+"&n="+n+"\">下一页</a></li>";
	
	out.println(page_btn_HTML);
%>
    
  </ul>
</nav>
</div>	

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>