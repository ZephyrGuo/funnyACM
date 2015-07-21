<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="
DB.SubmitRecordORM,
DB.SubmitRecord,
DB.UserORM
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
				<h2>Your Code</h2>
				</div>
			</div>			
		</div>		
	</div>
	<!----//header---->
	
	<hr><br/><br/><br/>
	
	<div class="container">
	
		<pre class="alert alert-info alert-dismissible" role="alert">
<%
		String param = request.getParameter("smt_id");
		String user_id = (String)request.getSession().getAttribute("handle");
				
		//arguments invaild
		if(param==null | user_id==null){
			response.sendRedirect("./Error.jsp?msg=Access Violation");
			return ;
		}
		
		int smt_id = Integer.parseInt(param);
		int user_type = (Integer)request.getSession().getAttribute("type");
		
		SubmitRecord sr = SubmitRecordORM.getInstance().loadById(smt_id);
		
		//no such submit record or permission denied		
		if(sr==null || (!sr.getSmt_user().equals(user_id) && user_type!=2)){
			response.sendRedirect("./Error.jsp?msg=404,the page does not exist.");
			return ;			
		}
				
		String detail = SubmitRecordORM.getInstance().loadCode(smt_id);
		
		detail = detail.replaceAll("<","&lt;");
		detail = detail.replaceAll(">","&gt;");
		
		out.println(detail);
%>			
		</pre>

	</div>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>