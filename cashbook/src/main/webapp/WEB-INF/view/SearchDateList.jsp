<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>SearchDateList</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<%
	List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
%>
<div class="container">
<br>
	<h2>
		<div class="container p-3 my-3 bg-secondary text-white text-center">SearchDateList</div>
	</h2>
	<div class="container p-3 my-3 bg-secondary text-white text-center"> 수입 / 지출별 검색 </div>
	<a href = "<%= request.getContextPath() %>/SearchIncomeExpendListController?kind=<%= "수입" %>" class = "btn btn-outline-dark btn-sm">수입</a>
	<a href = "<%= request.getContextPath() %>/SearchIncomeExpendListController?kind=<%= "지출" %>" class = "btn btn-outline-dark btn-sm">지출</a>
	<div class="container p-3 my-3 bg-secondary text-white text-center"> 날짜별 검색 </div>
	<form method = "get" action = "<%= request.getContextPath() %>/SearchDateListController">
		<input type = "date" name = "cashDate" class = "form-control">
		<button type = "submit" class = "btn btn-outline-dark btn-sm">검색</button>
	</form>
	<a href = "<%= request.getContextPath() %>/TagController" class = "btn btn-outline-dark btn-sm">이전 페이지</a>
	<table class = "table table-bordered">
		<tr>
			<th>tag</th>
			<th>cashbookNo</th>
			<th>cashDate</th>
			<th>kind</th>
			<th>memo</th>
			<th>updateDate</th>
			<th>createDate</th>
		</tr>
		<%
			for(Map<String, Object> map : list) {
		%>
				<tr>
					<td><%= map.get("tag") %></td>
					<td><%= map.get("cashbookNo") %></td>
					<td><%= map.get("cashDate") %></td>
					<td><%= map.get("kind") %></td>
					<td><%= map.get("memo") %></td>
					<td><%= map.get("updateDate") %></td>
					<td><%= map.get("createDate") %></td>
				</tr>
		<%
			}
		%> 
	</table>
</div>
</body>
</html>