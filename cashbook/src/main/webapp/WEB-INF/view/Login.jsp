<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<br>
	<h2>
		<div class="container p-3 my-3 bg-secondary text-white text-center">로그인</div>
	</h2>
	<form action = "<%= request.getContextPath() %>/LoginController" method = "post">
		<table class = "table table-bordered">
			<tr>
				<td>memberId</td>
				<td><input type = "test" name = "memberId" class = "form-control"></td>
			</tr>
			<tr>
				<td>memberPw</td>
				<td><input type = "password" name = "memberPw" class = "form-control"></td>
			</tr>
		</table>
		<button type = "submit" class = "btn btn-outline-secondary">로그인</button>
		<a href = "<%= request.getContextPath() %>/InsertMemberController" class = "btn btn-outline-secondary">회원가입</a>
	</form>
</div>
</body>
</html>