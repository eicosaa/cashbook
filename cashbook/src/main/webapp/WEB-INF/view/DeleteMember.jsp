<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DeleteMember</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div>
		<%= session.getAttribute("sessionMemberId") %>님 반갑습니다.
		<a href = "<%= request.getContextPath() %>/LogoutController">로그아웃</a>
	</div>
<div class="container">
<br>
	<h2>
		<div class="container p-3 my-3 bg-secondary text-white text-center">DeleteMember</div>
	</h2>
	<form method = "post" action = "<%= request.getContextPath() %>/DeleteMemberController">
		<table class = "table table-bordered">
			<tr>
				<td>ID</td>
				<td><input type = "text" name = "memberId" value = "<%= session.getAttribute("sessionMemberId") %>" readonly = "readonly" class = "form-control"></td>
			</tr>
			<tr>
				<td>PW</td>
				<td><input type = "password" name = "memberPw" class = "form-control"></td>
			</tr>
		</table>
		<div>
			<button type = "submit" class = "btn btn-outline-secondary">회원탈퇴</button>
			<a href = "<%= request.getContextPath() %>/SelectMemberOneController" class = "btn btn-outline-secondary">이전 페이지</a>
		</div>
	</form>
</div>
</body>
</html>