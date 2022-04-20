<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CashBookListByMonth</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<style>
		table.table.table-bordered td:HOVER {
		    background: #e7e7e7;
		}
	</style>
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
	 <table class="table table-bordered">
	 	<thead>
	 		<tr class = "text-center">
	 			<th>일</th>
	 			<th>월</th>
	 			<th>화</th>
	 			<th>수</th>
	 			<th>목</th>
	 			<th>금</th>
	 			<th>토</th>
	 		</tr>
	 	</thead>
	 	
	 	<tbody>
	 	<tr>
	 		<!-- 
	 			[(i - startBlank) > 0] : 1일 이전의 숫자들이 나오지 않도록 (ex) -2, -1, 0, 1)
	 			[(i - startBlank) <= endDay] : 해당 달의 숫자 이후의 숫자들이 나오지 않도록 (ex) 32, 33)
	 		-->
	 		<%
	 			for(int i = 1; i <= totalTd; i += 1) { 
	 				if((i - startBlank) > 0 && (i - startBlank) <= endDay) { // -존재하지 않는 날은 나오지 않도록
	 					String c = ""; // -일요일, 토요일 색깔 바꾸기 위한 변수
	 					if(i % 7 == 0) {
							c = "text-primary";
	 					} else if(i % 7 == 1) {
							c = "text-danger";
	 					}
	 		%>
	 					<td class = "<%= c %>">
	 						<%= i - startBlank %>
	 						<a href = "<%= request.getContextPath() %>/InsertCashBookController?y=<%= y %>&m=<%= m %>&d=<%= i - startBlank %>" class = "btn btn-outline-dark btn-sm">입력</a>
	 						<div>
	 							<%
	 								// 해당 날짜의 cashbook 목록 출력
	 								for(Map map : list) {
	 									if((Integer)map.get("day") == (i - startBlank)) {
	 							%>
	 										<div>
	 											<a href = "<%= request.getContextPath() %>/CashBookOneController?cashbookNo=<%= map.get("cashbookNo") %>">
			 										[<%= map.get("kind") %>] 
			 										<%= map.get("cash") %>원
			 										<%= map.get("memo") %>...
		 										</a>
	 										</div>
	 							<%
	 									}
	 								}
	 							%>
	 						</div>
	 					</td>
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
	 	</tbody>
	 </table>
</div>
</body>
</html>