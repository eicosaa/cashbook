<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>UpdateMember</title>
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
		<div class="container p-3 my-3 bg-secondary text-white text-center">회원정보 수정</div>
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
		<tr>
			<td>Name</td>
			<td><input type = "text" name = "memberName" class = "form-control"></td>
		</tr>
		<tr>
			<td>Gender</td>
			<td>
				<input type = "radio" name = "memberGender" value = "여성" class = "form-radio-input">여성 &nbsp;
				<input type = "radio" name = "memberGender" value = "남성" class = "form-radio-input">남성
			</td>
		</tr>
		<tr>
			<td>Phone</td>
			<td><input type = "number" name = "memberPhone1" class = "form-control"> -
				<input type = "number" name = "memberPhone2" class = "form-control"> -
				<input type = "number" name = "memberPhone3" class = "form-control"></td>
		</tr>
		<tr>
			<td>Email</td>
			<td>
				<input type = "text" name = "memberEmail1" class = "form-control"> @
				<select name = "memberEmail2">
					<option value = "email.com">email.com</option>
					<option value = "gmail.com">gmail.com</option>
					<option value = "naver.com">naver.com</option>
					<option value = "hanmail.com">hanmail.com</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Birth</td>
			<td><input type = "text" name = "memberBirth" class = "form-control"></td>
		</tr>
		<tr>
			<td>Address</td>
			<td><input type = "text" name = "memberAddr" class = "form-control"></td>
		</tr>
	</table>
	<button type = "submit" class = "btn btn-outline-secondary">회원 정보 수정</button>
	</form>
	<div>
		<a href = "<%= request.getContextPath() %>/CashBookListByMonthController" class = "btn btn-outline-secondary">이전 페이지</a>
	</div>
</div>
</body>
</html>