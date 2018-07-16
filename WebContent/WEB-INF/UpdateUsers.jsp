<%@ page language="java" import="java.util.*,domain.User" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>jsp修改用户</title>
</head>
<body>
	<img src='images/lu.gif' width=100px height=80px />欢迎jsp管理员
	<a href='/UserManager3/ManageUsers'>返回用户管理  </a>
	<a href='/UserManager3/Login'> 安全退出</a><hr/>
	<h1>修改用户</h1>
	<%
		User user=(User)request.getAttribute("user");
	%>
	<form action='/UserManager3/DelC?type=update' method='post'>
	<table border=1px bordercolor=green cellspacing=0 width=250px >
	<tr><td>Id</td><td><input type='text' name='Id' readonly value='<%=user.getId() %>'/></td></tr>
	<tr><td>用户名</td><td><input type='text' name='userName' value='<%=user.getName() %>'/></td></tr>
	<tr><td>Email</td><td><input type='text' name='Email' value='<%=user.getEmail() %>'/></td></tr>
	<tr><td>权限</td><td><input type='text' name='Grade' value='<%=user.getGrade() %>'/></td></tr>
	<tr><td>密码</td><td><input type='text' name='Pwd' value='<%=user.getPwd() %>'/></td></tr>
	<tr><td><input type='submit' value='修改用户'/></td><td><input type='reset' value='重新填写'/></td></tr>
	</table>
	</form>
		
	<hr/><img src='images/a03.jpg' width=200px height=100px />
</body>
</html>