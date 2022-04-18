<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.Map"%>
<%@ page import = "java.util.List"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CashBookListByMonth</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<%
		int y = (Integer)request.getAttribute("y");
		int m = (Integer)request.getAttribute("m");
	%>
<div class="container">
<br>
	<h2>
		<div class="container p-3 my-3 bg-secondary text-white text-center"><%= y %>년 <%= m %>월</div>
	</h2>
	<table class="table table-bordered text-center table-hover">
		<tr>
			<th>day</th>
			<th>kind</th>
			<th>cash</th>
		</tr>
		<%
			List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
			for(Map map : list) {
		%>
				<tr>
					<td><%= map.get("day") %></td>
					<td><%= map.get("kind") %></td>
					<td><%= map.get("cash") %></td>
				</tr>	
		<%
			}
		%>
	</table>
	<div>
		<a href = "<%= request.getContextPath() %>/CashBookListByMonthController?y=<%= y %>&m=<%= m - 1 %>" class = "btn btn-outline-secondary">이전</a>
		<a href = "<%= request.getContextPath() %>/CashBookListByMonthController?y=<%= y %>&m=<%= m + 1 %>" class = "btn btn-outline-secondary">다음</a>
	</div>
</body>
</html>