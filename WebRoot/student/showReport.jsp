<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="entity.*"%>
<jsp:useBean id="resourceMgr" class="entity.ResourceMgr" scope="application"/>
<jsp:useBean id="userMgr" class="entity.UserMgr" scope="application"/>
<jsp:useBean id="reportMgr" class="entity.ReportMgr" scope="application"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>我的举报页面</title>
    
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
							
							<div class="table-responsive" data-pattern="priority-columns" data-focus-btn-icon="fa-asterisk" data-sticky-table-header="true" data-add-display-all-btn="true" data-add-focus-btn="true">
							我的举报列表：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
								<table cellspacing="0" class="table table-small-font table-bordered table-striped">
									<thead>
										<tr>
										    <th>审核状态</th>
											<th data-priority="1">资源名</th>
											<th data-priority="1">举报原因</th>
											<th data-priority="3">举报日期</th>
											<th data-priority="1">发布人</th>
											<th data-priority="3">查看</th>
											<th data-priority="3">删除</th>
										
										</tr>
									</thead>
	  <%
     java.util.HashMap<String,Report> reportmap = new java.util.HashMap<String,Report>();
	  reportmap = reportMgr.getReportList();
	java.util.Iterator iterator = reportmap.keySet().iterator();

	while(iterator.hasNext()){
		
		Report report = (Report)reportmap.get(iterator.next());
		
		if(!report.getReporter().equals(session.getAttribute("usernum")))
			continue;
		User user=userMgr.getUser(report.getReporter());
		String reporter=user.getUsername();		
		Resource resource=resourceMgr.getResource(report.getResourcenum());
		String resourcename=resource.getResourcename();
		String resourceauthor=resource.getResourceauthor();
		User author=userMgr.getUser(resourceauthor);
		String state=null;
		switch (report.getPass()){
		case -1:state="待审核";
		        break;
		case 1:state="举报通过";
		       break;
		case 0:state="举报未通过";
		       break;
		}
		
%>
									<tbody>
										<tr>
										    <th> <span class="co-name"><%=state %></span></th>
											<td><%=resourcename %></td>
											<td><%=report.getReason() %></td>
											<td> <%=report.getReportdate() %></td>
											<td> <%=author.getUsername() %></td>
											<td><input type="button" align="right" value="查看" onClick="location.href='../student/showSingleResource.jsp?resourcenum=<%=resource.getResourcenum()%>'"></td>
											<td align="center"><a href="servlet/ReportServlet?operation=del&reportnum=<%=report.getReportnum() %>" onclick='return window.confirm("该举报将被删除，\n您确实要删除举报吗？")'>删除</a></td>
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