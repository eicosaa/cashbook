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

import dao.HashtagDao;

@WebServlet("/ThisTagListController")
public class ThisTagListController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -로그인 유/무에 따라 접근 허가
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId == null) {
			// 로그인 되지 않은 경우
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
		
		// -1) 해당 태그의 리스트 요청 분석
		String tag = "";
		if(request.getParameter("tag") != null) {
			tag = request.getParameter("tag");
		}
		System.out.println("[ThisTagListController.doGet()] tag : " + tag);
		
		// -2) 모델값(해당 태그의 리스트)을 반환하는 비즈니스 로직(모델) 호출
		HashtagDao hashtagDao = new HashtagDao();
		List<Map<String, Object>> list = hashtagDao.selectThisTagList(tag);
		
		request.setAttribute("list", list);
		
		// -3) 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/ThisTagList.jsp").forward(request, response);
	}

}
