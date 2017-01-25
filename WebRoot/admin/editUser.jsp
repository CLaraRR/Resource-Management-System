<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="entity.*"%>
<jsp:useBean id="userMgr" class="entity.UserMgr" scope="application" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>编辑用户</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">


<link rel="stylesheet" href="assets/css/fonts/linecons/css/linecons.css">
<link rel="stylesheet" href="assets/css/fonts/fontawesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/bootstrap.css">
<link rel="stylesheet" href="assets/css/xenon-core.css">
<link rel="stylesheet" href="assets/css/xenon-forms.css">
<link rel="stylesheet" href="assets/css/xenon-components.css">
<link rel="stylesheet" href="assets/css/xenon-skins.css">
<link rel="stylesheet" href="assets/css/custom.css">
<script src="assets/js/jquery-1.11.1.min.js"></script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

  <body class="page-body">

	
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
								    String usernum1=(String)session.getAttribute("usernum");
								    User user1=userMgr.getUser(usernum1);
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
					
						<a href="admin/main.jsp">
							<i class="linecons-cog"></i>
							<span class="title">用户管理</span>
						</a>
						
					
					
						<a href="admin/main.jsp">
							<i class="linecons-desktop"></i>
							<span class="title">资源管理</span>
						</a>
						
					
					
						<a href="admin/main.jsp">
							<i class="linecons-note"></i>
							<span class="title">任务管理</span>
						</a>
						
						
						<a href="admin/showReport.jsp">
							<i class="linecons-star"></i>
							<span class="title">举报处理</span>
						</a>

				</ul>
				</div>
			
		</div>
						

		
		<div class="main-content">
			
			<div class="row">
				<div class="col-sm-12">
					
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">编辑用户</h3>
							<div class="panel-options">
								<a href="#" data-toggle="panel">
									<span class="collapse-icon">&ndash;</span>
									<span class="expand-icon">+</span>
								</a>
								<a href="#" data-toggle="remove">
									&times;
								</a>
							</div>
						</div>
						<div class="panel-body">
							
							<form role="form" class="form-horizontal"  name="editUserform" method="post" action="servlet/UserServlet">
								 <input name="operation" type="hidden" id="operation" value="edit">
	 <%
         String usernum = request.getParameter("usernum"); 
   		 User user = userMgr.getUser(usernum);
     %>
								<div class="form-group">
									<label class="col-sm-2 control-label" for="field-1">学工号</label>
									
									<div class="col-sm-10">
										<input type="text" name="usernum" class="form-control" id="field-1" value="<%=usernum%>" readonly="true">
									</div>
								</div>
								
								<div class="form-group-separator"></div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label" for="field-2">用户名</label>
									
									<div class="col-sm-10">
										<input type="text" name="username" class="form-control" id="field-2" value="<%=user.getUsername() %>">
									</div>
								</div>
								
								<div class="form-group-separator"></div>
								<div class="form-group">
									<label class="col-sm-2 control-label">用户类型</label>
									<div class="col-sm-10">
									<div class="form-block">
											<label>
												<input type="radio" name="userType" class="cbr"  value="2" 
												<% 
                                                   if(user.getUserType() == 2)
        	                                           out.print("checked");
                                               %>>
												管理员
											</label>
											<br>
											<label>
												<input type="radio" name="userType"  value="1" class="cbr"
												<% 
                                                   if(user.getUserType() == 1)
        	                                           out.print("checked");
                                               %>>
												教师
											</label>
											<br>
											<label>
												<input type="radio" name="userType" value="0" class="cbr" 
												<% 
                                                   if(user.getUserType() == 0)
        	                                           out.print("checked");
                                               %>>
												学生
											</label>
											<br>
											
									</div>
									</div>
								</div>
								<div class="form-group-separator"></div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label" for="field-2">真实姓名</label>
									
									<div class="col-sm-10">
										<input type="text" name="realname" class="form-control" id="field-2" value="<%=user.getRealname()%>">
									</div>
								</div>
								<div class="form-group-separator"></div>
								<div class="form-group">
									<label class="col-sm-2 control-label">性别</label>
									
									<div class="col-sm-10">
										<div class="form-block">
											<label>
												<input type="radio" name="sex" class="cbr"  value="1" 
												<%
                                                  if(user.getSex() ==1)
        	                                           out.print("checked");
                                                %>>
												男
											</label>
											<br>
											<label>
												<input type="radio" name="sex"  class="cbr"  value="0" class="cbr"
												<%
                                                  if(user.getSex() ==0)
        	                                           out.print("checked");
                                                %>>
												女
											</label>
											<br>
									</div>
									</div>
								</div>
								<div class="form-group-separator"></div>
								<div class="form-group">
								<div class="col-sm-10">
								<div class="form-block">
										<div class="form-block">
							    <label>
                                     <input type="submit" name="Submit" value="提交">
                                </label>
                                </div>
                                </div>
                                </div>
                                </div>
							</form>
							
						</div>
					</div>
					
				</div>
			</div>
			
	
			
		</div>
		
			
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
		<!-- end: Chat Section -->
		
		
	</div>
	
	
	



	<!-- Bottom Scripts -->
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/TweenMax.min.js"></script>
	<script src="assets/js/resizeable.js"></script>
	<script src="assets/js/joinable.js"></script>
	<script src="assets/js/xenon-api.js"></script>
	<script src="assets/js/xenon-toggles.js"></script>


	<!-- JavaScripts initializations and stuff -->
	<script src="assets/js/xenon-custom.js"></script>

</body>

</html>