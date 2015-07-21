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

<link href="./css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="./css/style.css" rel="stylesheet" type="text/css" media="all" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<script src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/move-top.js"></script>
<script type="text/javascript" src="./js/easing.js"></script>
<script src="./js/menu_jquery.js"></script>
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
					<span class="menu"> </span>
					<ul>
						<li><a href="#home">home</a></li>
						<li><a href="#works" class="scroll">我们的目标</a></li>
					</ul>
				</div>
				<!---- script-nav ---->
				<script>
			$("span.menu").click(function(){
				$(".top-nav-left ul").slideToggle(500, function(){
				});
			});
			</script>
				<script type="text/javascript">
					jQuery(document).ready(function($) {
						$(".scroll").click(function(event){		
							event.preventDefault();
							$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
						});
					});
					</script>
				<!---- script-nav ---->
				<div class="top-nav-right">
					<div id="loginContainer">
						<a href="#" id="loginButton"><span>登录</span></a>
						<div id="loginBox">
							<form id="loginForm" method="post" action="./Login">
								<fieldset id="body">
									<fieldset>
										<label for="email">账号</label> <input type="text"
											name="user_id" id="login_user_id">
									</fieldset>
									<fieldset>
										<label for="password">密码</label> <input type="password"
											name="user_psw" id="login_user_psw">
									</fieldset>
									<input type="submit" id="login" value="登录">
									
									</label>
								</fieldset>
								<span><a href="#">忘记密码?</a></span>
							</form>
						</div>
					</div>
		
				</div>

			</div>
		</div>
	</div>
	<!----//header---->

	<!----banner---->
	<!----start-slider-script---->
	<script src="./js/responsiveslides.min.js"></script>
	<script>
			    // You can also use "$(window).load(function() {"
			    $(function () {
			      // Slideshow 4
			      $("#slider4").responsiveSlides({
			        auto: true,
			        pager: true,
			        nav: true,
			        speed: 500,
			        namespace: "callbacks",
			        before: function () {
			          $('.events').append("<li>before event fired.</li>");
			        },
			        after: function () {
			          $('.events').append("<li>after event fired.</li>");
			        }
			      });
			
			    });
			  </script>
	<!----//End-slider-script---->
	<!-- Slideshow 4 -->
	<div id="top" class="callbacks_container" id="home">
		<ul class="rslides" id="slider4">
			<li><img src="./images/banner.jpg" alt="">
				<div class="caption bounceInDown">
					<div class="slide-text-info">
						<h1>欢迎加入浙江大学城市学院ACM校队</h1>
						<p>come on!</p>
						<a class="banner-btn wow bounceIn" data-wow-delay="0.4s" href="#" data-target="#myModal" data-toggle="modal">Become
							a Member</a>
					</div>
				</div></li>
		</ul>
	</div>
	<div class="clearfix"></div>
	<!----- //End-slider---->
	<!---//banner--->
	<!----content---->
	<div class="content">
		<div class="container">
			<div id="works" class="content-work">
				<div class="content-top">
					<img class="ball wow bounceIn" data-wow-delay="0.4s"
						src="./images/ball.png">
					<h2>我们的目标</h2>
					<p class="para">参加ACM可以提高编程水平，学习能力。培养团队合作能力，认识更多牛人。在为学校获取荣誉的同时，ACM的参赛经历也是一块很好的敲门砖。</p>
					<p class="para-p">ACM国际大学生程序设计竞赛(英文全称：ACM International 
					Collegiate Programming Contest（ACM-ICPC或ICPC）是由美国计算机协会（ACM）主办的，
					一项旨在展示大学生创新能力、团队精神和在压力下编写程序、分析和解决问题能力的年度竞赛。经过近30多年的发展
					，ACM国际大学生程序设计竞赛已经发展成为最具影响力的大学生计算机竞赛。
					</p>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="./js/jquery.flexisel.js"></script>
	<!----content---->
	<!----footer---->
	<div class="footer">
		<div class="container">
			<div class="footer-class">
				<p>&copy; 2014-2015 Develop by 郭泽晖</p>
			</div>
		</div>
		<script type="text/javascript">
						$(document).ready(function() {
							$().UItoTop({ easingType: 'easeOutQuart' });
							
						});
					</script>
		<a href="#" id="toTop" style="display: block;"> <span
			id="toTopHover" style="opacity: 1;"> </span></a>
	</div>
	<!----footer---->
	
	<!-- regist Modal -->
	<div class="modal fade bs-example-modal-sm" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">注册</h4>
	      </div>    
	      <div class="modal-body">
		      <form class="form-signin">
		        <h2 class="form-signin-heading">请填写真实信息</h2>	        
	        	<input type="text" class="form-control" placeholder="学号" name="user_id" id="user_id" required autofocus>
	        	<input type="text" class="form-control" placeholder="姓名" name="user_name" id="user_name" required autofocus>
	        	<input type="password" class="form-control" placeholder="密码" name="user_psw" id="user_psw" required>
				<input type="password" class="form-control" placeholder="确认密码" name="confirm_psw" id="confirm_psw" required>		    
			    <br/>
			    <p><font color="#d9534f" id="regist_hint"></font></p>
	      	  </form>
	      </div>
	      <div class="modal-footer">
	        	<button class="btn btn-primary" onclick="regist()">注册</button>
	      </div>
	    </div>
	  </div>
	</div>
	

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>