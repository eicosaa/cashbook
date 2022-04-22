package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CashBookDao;
import dao.MemberDao;
import vo.CashBook;
import vo.Member;

@WebServlet("/SelectMemberOneController")
public class SelectMemberOneController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -로그인 유/무에 따라 접근 허가
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId == null) {
			// 로그인 되지 않은 경우
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
		
		// -회원 정보 상세보기 요청 분석
		/*
		1. String memberId = (String) session.getAttribute("sessionMemberId");
		2. String memberId = null;
			if (request.getParameter(memberId) != null) { 
				memberId = request.getParameter(memberId);
			}
		*/
		System.out.println("[SelectMemberOneController.doGet()] memberId : " + sessionMemberId);
		
		// -모델값(회원 정보 상세보기)을 반환하는 비즈니스 로직(모델) 호출
		MemberDao memberDao = new MemberDao();
		Member member = memberDao.selectMemberOne(sessionMemberId);
		
		// -해당 링크 상세보기 출력에 필요한 모델값
		request.setAttribute("memberId", member.getMemberId());
		request.setAttribute("memberName", member.getMemberName());
		request.setAttribute("memberGender", member.getMemberGender());
		request.setAttribute("memberPhone", member.getMemberPhone());
		request.setAttribute("memberBirth", member.getMemberBirth());
		request.setAttribute("memberEmail", member.getMemberEmail());
		request.setAttribute("memberAddr", member.getMemberAddr());
		request.setAttribute("createDate", member.getCreateDate());
		
		// 3) 뷰 forward
		request.getRequestDispatcher("/WEB-INF/view/MemberOne.jsp").forward(request, response);
	}
}
