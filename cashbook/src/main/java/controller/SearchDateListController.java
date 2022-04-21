package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashBookDao;
import dao.HashtagDao;
import vo.CashBook;

@WebServlet("/SearchDateListController")
public class SearchDateListController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 날짜별 검색 리스트 요청 분석
		String cashDate = "";
		if(request.getParameter("cashDate") != null) {
			cashDate = request.getParameter("cashDate");
		}
		System.out.println("[SearchDateListController.doGet()] cashDate : " + cashDate);
		
		// 2) 모델값(날짜별 검색 리스트)을 반환하는 비즈니스 로직(모델) 호출
		HashtagDao hashtagDao = new HashtagDao();
		List<Map<String, Object>> list = hashtagDao.selectSearchDateList(cashDate);
		
		request.setAttribute("list", list);
		
		// 3) 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/SearchDateList.jsp").forward(request, response);
	}
}
