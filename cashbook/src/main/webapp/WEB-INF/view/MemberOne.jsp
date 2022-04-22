<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>MemberOne</title>
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
		<div class="container p-3 my-3 bg-secondary text-white text-center">회원정보 상세보기</div>
	</h2>
	
	<table class = "table table-bordered">
		<tr>
			<td>memberId</td>
			<td><%= request.getAttribute("memberId") %></td>
		</tr>
		<tr>
			<td>memberPw</td>
			<td>****</td>
		</tr>
		<tr>
			<td>createDate</td>
			<td><%= request.getAttribute("createDate") %></td>
		</tr>
	</table>
	<div>
		<a href = "<%= request.getContextPath() %>/UpdateMemberController?memberId=<%= request.getAttribute("memberId") %>" class = "btn btn-outline-secondary">회원 정보 수정</a>
		<a href = "<%= request.getContextPath() %>/DeleteMemberController?memberId=<%= request.getAttribute("memberId") %>" class = "btn btn-outline-secondary">회원 탈퇴</a>
		<a href = "<%= request.getContextPath() %>/CashBookListByMonthController" class = "btn btn-outline-secondary">이전 페이지</a>
	</div>
</div>
</body>
</html>