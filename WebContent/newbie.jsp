<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
DB.Section,
DB.SectionORM
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

<%
	int sectionN;

	if(request.getParameter("sectionN")!=null)
		sectionN = Integer.parseInt(request.getParameter("sectionN"));
	else 
		sectionN = -1;
	
	if(sectionN>=3 || sectionN<0){
		response.sendRedirect("./Error.jsp?msg=no the section");
		return ;
	}
	
	String user_id = (String)request.getSession().getAttribute("handle");
	//String type = request.getSession().getAttribute("type");
	Section sec = SectionORM.getInstance().loadSection(sectionN, user_id);
	
	if(!sec.isComplete()){
		if(sectionN>0){
			if(!SectionORM.getInstance().loadSection(sectionN-1, user_id).isComplete()){
				response.sendRedirect("./newbie.jsp?sectionN="+(sectionN-1));
				return ;
			}
		}
	}
	
	String title = null;
	
	switch(sectionN){
	case 0: title = new String("第一章"); break;
	case 1: title = new String("第二章"); break;
	case 2: title = new String("第三章"); break;
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
				<h2>入门闯关&nbsp;<small><%=title%></small></h2>
				</div>
			</div>
			
		</div>
		
	</div>
	<!----//header---->
	<div class="modal-footer"></div>
<div class="container">
	<div class="panel panel-default">
	
		  <div class="panel-body">
		    	<table class="table table-bordered">
		  			<tr><td>#</td><td>题目</td></tr>
<%
					String[] prb_id_list = sec.getPrb_id_list();
					String[] prb_name_list = sec.getPrb_name_list();

					for(int i=0;i<prb_id_list.length;i++){
						out.println("<tr><td>"+(char)('A'+i)+"</td>");
						out.println("<td><a href=\"./problem.jsp?prb_id="+prb_id_list[i]+"&type=2&undetermined_id=0&prb_idx="
								+(i+sectionN*4)+"\">");		
						out.println(prb_name_list[i]+"</a></td></tr>");
					}
%>		  					  			
				</table>
		  </div>
		  
	 	 <div class="panel-footer" align="center">
	 	 	 
<%
		 if(sec.isComplete()){
			 out.println("<a class=\"btn btn-primary\" href=\"./newbie.jsp?sectionN="+
		 			(sectionN+1)+"\">下一章</a>");
		 }else{
			 if(sectionN != 2){
			 	out.println("<a class=\"btn btn-primary\" href=\"#\">下一章(未解锁)</a>");
			 }
		 }
%>
	 	 </div>
 	 
	</div>
</div>
	
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>