<%@page import="java.util.ArrayList"%>
<%@ page language="java" import="java.util.*,domain.User" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>jsp用户管理</title>
</head>
<body>
	<img src='images/lu.gif' width=100px height=80px />欢迎jsp管理员 
				<a href='/UserManager3/LoginC'>返回主界面  </a>
				<a href='/UserManager3/index.jsp'>安全退出</a><hr/>
	<h1>管理用户</h1>
	<table border=1px bordercolor=green cellspacing=0 width=500px >
			<tr><th>ID</th><th>用户名</th><th>权限</th><th>Email</th><th>删除用户</th><th>修改用户</th></tr>
			<!-- 循环 -->
			<%
				ArrayList<User> al=(ArrayList<User>)request.getAttribute("al");
				for(User u:al){
					%>
					<tr><td><%=u.getId() %></td>
					<td><%=u.getName() %></td>				
					<td><%=u.getGrade() %></td>
					<td><%=u.getEmail() %></td>
					<td><a onClick='return confirmOper();' href='/UserManager3/DelC?type=del&id=<%=u.getId() %>'>删除用户</a></td>
					<td><a href='/UserManager3/DelC?type=gotoUpDateV&id=<%=u.getId() %>'>修改用户</a></td></tr>
					<% 
				}
			%>
			</table>
			<%
				int pageCount=(Integer)request.getAttribute("pagecount");
			for(int i=1;i<=pageCount;i++) {
				%>
					<a href='/UserManager3/gotoManageUsers?pageNow=<%=i %>'><<%=i %>></a>
				<%
				}
			%>
</body>
</html>