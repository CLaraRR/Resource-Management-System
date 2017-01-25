<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="entity.*"%>
<jsp:useBean id="userMgr" class="entity.UserMgr" scope="application"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>管理员页面</title>
    
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

						<div class="panel-body">
							
							<div class="table-responsive" data-pattern="priority-columns" data-focus-btn-icon="fa-asterisk" data-sticky-table-header="true" data-add-display-all-btn="true" data-add-focus-btn="true">
							用户列表：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 <input type="button" align="right" value="添加用户" onClick="location.href='../admin/addUser.jsp'">
								<table cellspacing="0" class="table table-small-font table-bordered table-striped">
									<thead>
										<tr>
											<th>学工号</th>
											<th data-priority="1">用户名</th>
											<th data-priority="3">真实姓名</th>
											<th data-priority="1">性别</th>
											<th data-priority="3">用户类型</th>
											<th data-priority="3">编辑</th>
											<th data-priority="6">删除</th>
										</tr>
									</thead>
	<%
	java.util.HashMap<String,User> usermap = new java.util.HashMap<String,User>();
	usermap = userMgr.getUserList();
	java.util.Iterator iterator = usermap.keySet().iterator();

	while(iterator.hasNext()){
		User user = (User)usermap.get(iterator.next());
		int userType = user.getUserType();
		String userTypeName = "";
		switch(userType){
		case 0:
			userTypeName = "学生";
			break;
		case 1:
			userTypeName = "教师";
			break;
		case 2:
			userTypeName = "管理员";
			break;
		}
   %>
									<tbody>
										<tr>
											<th> <span class="co-name"><%=user.getUsernum()%></span></th>
											<td><%=user.getUsername()%></td>
											<td><%=user.getRealname()%></td>
											<td>
                                              <%
                                                 if(user.getSex() ==1){
                                                      out.println("男");
                                                 }
                                                 if(user.getSex() ==0){
                                                      out.println("女");
                                                 }
                                              %>
											</td>
											<td><%=userTypeName%></td>
											<td><input type="button" align="right" value="编辑" onClick="location.href='../admin/editUser.jsp?usernum=<%=user.getUsernum()%>'"></td>
											<td><a href="servlet/UserServlet?operation=del&username=<%=user.getUsername()%>&userType=<%=userType %>" onclick='return window.confirm("该用户将被删除，\n您确实要删除用户吗？")'>删除</a></td>
										</tr>	
										  <%
                                         	}
                                          %>	
								</table>
							
							</div>
							
							<script type="text/javascript">
							// This JavaScript Will Replace Checkboxes in dropdown toggles
							jQuery(document).ready(function($)
							{
								setTimeout(function()
								{
									$(".checkbox-row input").addClass('cbr');
									cbr_replace();
								}, 0);
							});
							</script>
								
							
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
