package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import vo.Member;

@WebServlet("/DeleteMemberController")
public class DeleteMemberController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -로그인 유/무에 따라 접근 허가
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId == null) {
			// 로그인 되지 않은 경우
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
				
		// -뷰 forward
		request.getRequestDispatcher("/WEB-INF/view/DeleteMember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		// -탈퇴 할 회원 정보 요청 분석
		String memberId = null;
		String memberPw = null;
		
		Member member = new Member();
		if(request.getParameter("memberId") != null) {
			memberId = request.getParameter("memberId");
			member.setMemberId(memberId);
		}
		if(request.getParameter("memberPw") != null) {
			memberPw = request.getParameter("memberPw");
			member.setMemberPw(memberPw);			
		}
		
		System.out.println("[DeleteMemberController.doPost()] memberId : " + memberId);
		System.out.println("[DeleteMemberController.doPost()] memberPw : " + memberPw);
		
		// -모델값(회원 가입 정보)을 반환하는 비즈니스 로직(모델) 호출
		MemberDao memberDao = new MemberDao();
		memberDao.deleteMember(memberId, memberPw);
		
		// -뷰 redirect
		response.sendRedirect(request.getContextPath() + "/LogoutController");
	}

}
