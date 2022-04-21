<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>TagList</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<%
	List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
%>
<div class="container">
<br>
	<h2>
		<div class="container p-3 my-3 bg-secondary text-white text-center">tag rank</div>
	</h2>
	<div> 수입 / 지출별 검색 </div>
	<a href = "<%= request.getContextPath() %>/SearchIncomeExpendListController?kind=<%= "수입" %>" class = "btn btn-outline-dark btn-sm">수입</a>
	<a href = "<%= request.getContextPath() %>/SearchIncomeExpendListController?kind=<%= "지출" %>" class = "btn btn-outline-dark btn-sm">지출</a>
	<div> 날짜별 검색 </div>
	
	<table class = "table table-bordered">
		<tr>
			<th>rank</th>
			<th>tag</th>
			<th>count</th>
		</tr>
		<%
			for(Map<String, Object> map : list) {
		%>
				<tr>
					<td><%= map.get("rank") %></td>
					<td><a href = "#"><%= map.get("tag") %></a></td>
					<td><%= map.get("cnt") %></td>
				</tr>
		<%
			}
		%> 
	</table>
</div>
</body>
</html>