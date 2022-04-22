<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>InsertMember</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<br>
	<h2>
		<div class="container p-3 my-3 bg-secondary text-white text-center">InsertMember</div>
	</h2>
	<form method = "post" action = "<%= request.getContextPath() %>/InsertMemberController">
		<table class = "table table-bordered">
			<tr>
				<td>ID</td>
				<td><input type = "text" name = "memberId" class = "form-control"></td>
			</tr>
			<tr>
				<td>PW</td>
				<td><input type = "password" name = "memberPw" class = "form-control"></td>
			</tr>
		</table>
		<button type = "submit" class = "btn btn-outline-secondary">회원가입</button>
	</form>
</div>
</body>
</html>