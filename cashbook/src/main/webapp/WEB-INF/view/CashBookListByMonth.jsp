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
		List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
		int y = (Integer)request.getAttribute("y");
		int m = (Integer)request.getAttribute("m");
		
		int startBlank = (Integer)request.getAttribute("startBlank");
		int endDay = (Integer)request.getAttribute("endDay");
		int endBlank = (Integer)request.getAttribute("endBlank");
		int totalTd = (Integer)request.getAttribute("totalTd");
		
		System.out.println("[CashBookListByMonth.jsp] list.size() : " + list.size());
		System.out.println("[CashBookListByMonth.jsp] list.size() : " + y);
		System.out.println("[CashBookListByMonth.jsp] m : " + m);
		
		System.out.println("[CashBookListByMonth.jsp] startBlank : " + startBlank);
		System.out.println("[CashBookListByMonth.jsp] endDay : " + endDay);
		System.out.println("[CashBookListByMonth.jsp] endBlank : " + endBlank);
		System.out.println("[CashBookListByMonth.jsp] totalTd : " + totalTd);
	%>
<div class="container">
<br>
	<h2>
		<div class="container p-3 my-3 bg-secondary text-white text-center"><%= y %>년 <%= m %>월</div>
	</h2>

	<div>
		<a href = "<%= request.getContextPath() %>/CashBookListByMonthController?y=<%= y %>&m=<%= m - 1 %>" class = "btn btn-outline-secondary">이전 달</a>
		<a href = "<%= request.getContextPath() %>/CashBookListByMonthController?y=<%= y %>&m=<%= m + 1 %>" class = "btn btn-outline-secondary">다음 달</a>
	</div>
	<!-- 
		1) 이번 날 1일의 요일 firstDay
		2) 요일 -> startBlank -> 일 0, 월 1, 화 2, ... 토 6
		3) 이번 달 마지막 날짜 endDay
		4) endBlank -> totalBlank
		5) td의 개수 1 ~ totalBlank
				+
		6) 가계부 list
		7) 오늘 날짜
	 -->
	 
	 <!-- -달력 -->
	 <table class="table table-bordered text-center table-hover">
	 	<tr>
	 		<!-- 
	 			[(i - startBlank) > 0] : 1일 이전의 숫자들이 나오지 않도록 (ex) -2, -1, 0, 1)
	 			[(i - startBlank) <= endDay] : 해당 달의 숫자 이후의 숫자들이 나오지 않도록 (ex) 32, 33)
	 		-->
	 		<%
	 			for(int i = 1; i <= totalTd; i += 1) { 
	 				if((i - startBlank) > 0 && (i - startBlank) <= endDay) { // -존재하지 않는 날은 나오지 않도록
	 		%>
	 					<td><%= i - startBlank %></td>
	 		<%
	 				} else {
	 		%>
	 					<td>&nbsp;</td>
	 		<%
	 				}
	 				if(i < totalTd && i % 7 == 0) {
	 		%>
	 					</tr><tr> <!-- 새로운 행을 추가시키기 위해 -->
	 		<%			
	 				}
	 			}
	 		%>
	 	</tr>
	 </table>
</body>
</html>