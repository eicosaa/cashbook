<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>UpdateCashBook</title>
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
		<div class="container p-3 my-3 bg-secondary text-white text-center">UpdateCashBook</div>
	</h2>
	<form method = "post" action = "<%= request.getContextPath() %>/InsertCashBookController">
	<table class = "table table-bordered">
			<tr>
				<td>Date</td>
				<td><input type = "text" name = "cashDate" value = "<%= (String)request.getAttribute("cashDate") %>" readonly = "readonly" class = "form-control"></td>
			</tr>
			<tr>
				<td>kind</td>
				<td>
					<input type = "radio" name = "kind" value = "수입" class = "form-radio-input">수입 &nbsp;
					<input type = "radio" name = "kind" value = "지출" class = "form-radio-input">지출
				</td>
			</tr>
			<tr>
				<td>cash</td>
				<td><input type = "number" name = "cash" value = "<%= (String)request.getAttribute("cash") %>" class = "form-control"></td>
			</tr>
			<tr>
				<td>memo</td>
				<td>
					<textarea rows="4" cols="50" name = "memo" value = "<%= (String)request.getAttribute("memo") %>" class = "form-control"></textarea>
				</td>
			</tr>
		</table>
		<button type = "submit" class = "btn btn-outline-secondary">입력</button>
		<a href = "<%= request.getContextPath() %>/CashBookListByMonthController" class = "btn btn-outline-secondary">이전 페이지</a>
	</form>
</body>
</html>