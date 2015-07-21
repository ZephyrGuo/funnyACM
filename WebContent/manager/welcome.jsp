<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
	base.Task,
	event.LoadAllTaskTplTask,
	DB.TaskTemplateORM
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
				$(window.parent.document).find("#manage_page").height($("body").height()+30);
			}	
		)
</script>

</head>

<body>
	
<div class="jumbotron">
  <h1>Welocome!</h1>
</div>
	
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../dist/js/bootstrap.min.js"></script>
</body>
</html>