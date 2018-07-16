<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>jsp登陆</title>
</head>
<body>
	<div background-image src='images/a01.jpg' >
	<img src='images/lu.gif' width=100px height=80px /><hr/>
	<h1>用户登录</h1>
	<form action='/UserManager3/LoginC' method='post'>
		用户名：<input type='text' name='username'/><br/>
		密  码：  <input type='password' name='password'/><br/>
		<input type='submit' value='登录'/><br/>
	</form>
	<hr/><img src='images/a03.jpg' width=200px height=100px />
</body>
</html>