package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CashBookDao;
import dao.HashtagDao;
import vo.CashBook;

@WebServlet("/SearchDateListController")
public class SearchDateListController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -로그인 유/무에 따라 접근 허가
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId == null) {
			// 로그인 되지 않은 경우
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
				
		// 1) 날짜별 검색 리스트 요청 분석
		String beginCashDate = "";
		String lastCashDate = "";
		if(request.getParameter("beginCashDate") != null) {
			beginCashDate = request.getParameter("beginCashDate");
		}
		if(request.getParameter("lastCashDate") != null) {
			lastCashDate = request.getParameter("lastCashDate");
		}
		System.out.println("[SearchDateListController.doGet()] beginCashDate : " + beginCashDate);
		System.out.println("[SearchDateListController.doGet()] lastCashDate : " + lastCashDate);
		
		// 2) 모델값(날짜별 검색 리스트)을 반환하는 비즈니스 로직(모델) 호출
		HashtagDao hashtagDao = new HashtagDao();
		List<Map<String, Object>> list = hashtagDao.selectSearchDateList(beginCashDate, lastCashDate);
		
		request.setAttribute("list", list);
		
		// 3) 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/SearchDateList.jsp").forward(request, response);
	}
}
