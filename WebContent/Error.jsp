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
<script src="./js/jquery.min.js"></script>
<link href="./css/animate.css" rel="stylesheet" type="text/css" media="all">
<link href="./css/funnyACM.css" rel="stylesheet" type="text/css" media="all">
<script src="./js/funnyACM.js"></script>

</head>
<body>

<%
 if(request.getParameter("msg")==null || request.getParameter("msg").length()==0){
	 response.sendRedirect("./Error.jsp?msg=Access error");
	 return ;
 }

%>

	<!----header---->
	<div class="header">
		<div class="container">
			<div class="logo">
				<img src="./images/logo.png">
			</div>
				<div class="top-nav">
				<div class="top-nav-left">
				<h2>╮(╯▽╰)╭</h2>
				</div>
			</div>			
		</div>
		
	</div>
	<!----//header---->
	
	<hr><br/><br/><br/><br/><br/>
	
	<div class="container">
	
		<div class="alert alert-danger alert-dismissible" role="alert">
			<h2>
  				<strong>Warning:</strong> <%=request.getParameter("msg")%>
			</h2>
		</div>
	
	</div>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>