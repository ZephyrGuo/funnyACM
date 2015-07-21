<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%@ page import="
	common.Configuration,
	java.util.Properties,
	DB.TaskTemplateORM,
	DB.TaskTemplate,
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

	td{
		padding:8px;
	}

</style>


</head>

<body>
<%
	  Properties conf = Configuration.load("conf/common.properties");
	  String oj_list = conf.getProperty("oj_list");
	  String[] list = oj_list.split(";");     
      TaskTemplate tpl[] = new TaskTemplate[3];
      Problem[][] prb = new Problem[3][];
      String select_html;
      
      for(int i=0;i<3;i++){
    	  tpl[i] = TaskTemplateORM.getInstance().loadById(i);
      	  String[] p = tpl[i].getPrb_list().split(";");
      	  prb[i] = new Problem[p.length];
      	  	  
      	  for(int j=0;j<p.length;j++){
      	 	 prb[i][j] = ProblemORM.getInstance().LoadInfoByPrbId(Integer.parseInt(p[j]));
      	  }
      }     
%>


<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">第一章</h3>
  </div>
  <div class="panel-body">
  	<table>
<%	
	for(int i=0;i<prb[0].length;i++){	
		select_html = "<select>";		
		for(int j=0;j<list.length;j++){
			if(list[j].equals(prb[0][i].getOj_name()))
			 	select_html += "<option selected=\"selected\" value=\""+list[j]+"\">"+list[j]+"</option>";
			else
				select_html += "<option value=\""+list[j]+"\">"+list[j]+"</option>";
		}   
		select_html += "</select>";
	
		out.println("<tr><td>"+(char)('A'+i)+"</td><td>"+select_html+"</td>");
		out.println("<td><input type=\"number\" value=\""+prb[0][i].getOj_id()+"\">");
		out.println("</td></tr>");
	}
%>
	</table>
  </div>
</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">第二章</h3>
  </div>
  <div class="panel-body">
  	  	<table>
<%	
	for(int i=0;i<prb[1].length;i++){	
		select_html = "<select>";		
		for(int j=0;j<list.length;j++){
			if(list[j].equals(prb[1][i].getOj_name()))
			 	select_html += "<option selected=\"selected\" value=\""+list[j]+"\">"+list[j]+"</option>";
			else
				select_html += "<option value=\""+list[j]+"\">"+list[j]+"</option>";
		}   
		select_html += "</select>";
	
		out.println("<tr><td>"+(char)('A'+i)+"</td><td>"+select_html+"</td>");
		out.println("<td><input type=\"number\" value=\""+prb[1][i].getOj_id()+"\">");
		out.println("</td></tr>");
	}
%>
		</table>
  </div>
</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">第三章</h3>
  </div>
  <div class="panel-body">
   	  	<table>
<%	
	for(int i=0;i<prb[2].length;i++){	
		select_html = "<select>";		
		for(int j=0;j<list.length;j++){
			if(list[j].equals(prb[2][i].getOj_name()))
			 	select_html += "<option selected=\"selected\" value=\""+list[j]+"\">"+list[j]+"</option>";
			else
				select_html += "<option value=\""+list[j]+"\">"+list[j]+"</option>";
		}   
		select_html += "</select>";
	
		out.println("<tr><td>"+(char)('A'+i)+"</td><td>"+select_html+"</td>");
		out.println("<td><input type=\"number\" value=\""+prb[2][i].getOj_id()+"\">");
		out.println("</td></tr>");
	}
%>
		</table>
  </div>
</div>

	<div align="center">	
		<button type="button" class="btn btn-primary" onclick="saveSection()">保存</button>
	</div>
	
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../dist/js/bootstrap.min.js"></script>
</body>
</html>