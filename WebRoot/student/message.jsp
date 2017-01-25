<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="entity.*"%>
<jsp:useBean id="userMgr" class="entity.UserMgr" scope="application"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content ="3;url=../student/main.jsp">

<link rel="stylesheet" href="assets/css/fonts/linecons/css/linecons.css">
<link rel="stylesheet" href="assets/css/fonts/fontawesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/bootstrap.css">
<link rel="stylesheet" href="assets/css/xenon-core.css">
<link rel="stylesheet" href="assets/css/xenon-forms.css">
<link rel="stylesheet" href="assets/css/xenon-components.css">
<link rel="stylesheet" href="assets/css/xenon-skins.css">
<link rel="stylesheet" href="assets/css/custom.css">
<script src="assets/js/jquery-1.11.1.min.js"></script>
<title>文件上传结果</title>
</head>
   <body class="page-body"  onload="shownum()">

	
	<div class="page-container"><!-- add class "sidebar-collapsed" to close sidebar by default, "chat-visible" to make chat appear always -->
			
		<!-- Add "fixed" class to make the sidebar fixed always to the browser viewport. -->
		<!-- Adding class "toggle-others" will keep only one menu item open at a time. -->
		<!-- Adding class "collapsed" collapse sidebar root elements and show only icons. -->
		<div class="sidebar-menu toggle-others fixed">
			
			<div class="sidebar-menu-inner">	
				
				<header class="logo-env">
					
					<!-- logo -->
					<div class="logo">
						<li class="dropdown user-profile">
						<a href="#" data-toggle="dropdown">
							<img src="assets/images/user-4.png" alt="user-image" class="img-circle img-inline userpic-32" width="28" />
							<span >
							<font color="white">
							   欢迎你！
							    <%
								    String usernum=(String)session.getAttribute("usernum");
								    User user1=userMgr.getUser(usernum);
								    out.println(user1.getUsername());
								%>
							</font>
								
							<i class="fa-angle-down"></i>
							</span>
						</a>
						
						<ul class="dropdown-menu user-profile-menu list-unstyled">
							
							<li>
								<a href="#settings">
									<i class="fa-wrench"></i>
									设置
								</a>
							</li>
							
							<li>
								<a href="#help">
									<i class="fa-info"></i>
									帮助
								</a>
							</li>
							<li class="last">
								<a href="servlet/LoginOutServlet">
									<i class="fa-lock"></i>
									退出
								</a>
							</li>
						</ul>
					</li>
					</div>		
				</header>
						
				
						
				<ul id="main-menu" class="main-menu">
					<!-- add class "multiple-expanded" to allow multiple submenus to open -->
					<!-- class "auto-inherit-active-class" will automatically add "active" class for parent elements who are marked already with class "active" -->
					
						<a href="student/main.jsp">
							<i class="linecons-cog"></i>
							<span class="title">共享资源</span>
						</a>
						
					
					
						<a href="student/showResource.jsp">
							<i class="linecons-desktop"></i>
							<span class="title">我的资源</span>
						</a>
						
					
					
						<a href="student/showTask.jsp">
							<i class="linecons-note"></i>
							<span class="title">我的任务</span>
						</a>
						
						<a href="student/showTask.jsp">
							<i class="linecons-star"></i>
							<span class="title">我的举报</span>
						</a>

				</ul>
						
			</div>
			
		</div>

						<div class="panel-body">
							
							<center>
        <h2>${message}</h2>
        <h5 id="time"></h5>
    </center>
</body>
<script type="text/javascript"> 
   var i = 4; 
   function shownum(){ 
      i=i-1; 
      document.getElementById("time").innerHTML=i+"秒后自动返回任务界面";
      setTimeout('shownum()',1000); 
   } 
</script>
		
		
			
		<!-- start: Chat Section -->
		<div id="chat" class="fixed">
			
			<div class="chat-inner">
			
				
				<h2 class="chat-header">
					<a href="#" class="chat-close" data-toggle="chat">
						<i class="fa-plus-circle rotate-45deg"></i>
					</a>
					
					Chat
					<span class="badge badge-success is-hidden">0</span>
				</h2>
				
				<script type="text/javascript">
				// Here is just a sample how to open chat conversation box
				jQuery(document).ready(function($)
				{
					var $chat_conversation = $(".chat-conversation");
					
					$(".chat-group a").on('click', function(ev)
					{
						ev.preventDefault();
						
						$chat_conversation.toggleClass('is-open');
						
						$(".chat-conversation textarea").trigger('autosize.resize').focus();
					});
					
					$(".conversation-close").on('click', function(ev)
					{
						ev.preventDefault();
						$chat_conversation.removeClass('is-open');
					});
				});
				</script>
				
				
				
				
				
				
				
				
				
			
			</div>
			
		</div>
		
			
		
		
		
		
	
	
	
	



	<!-- Bottom Scripts -->
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/TweenMax.min.js"></script>
	<script src="assets/js/resizeable.js"></script>
	<script src="assets/js/joinable.js"></script>
	<script src="assets/js/xenon-api.js"></script>
	<script src="assets/js/xenon-toggles.js"></script>


	<!-- Imported scripts on this page -->
	<script src="assets/js/rwd-table/js/rwd-table.min.js"></script>


	<!-- JavaScripts initializations and stuff -->
	<script src="assets/js/xenon-custom.js"></script>

</body>
</html>