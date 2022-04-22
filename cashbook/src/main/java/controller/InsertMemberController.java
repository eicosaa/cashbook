package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import vo.Member;

@WebServlet("/InsertMemberController")
public class InsertMemberController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -뷰 forward
		request.getRequestDispatcher("/WEB-INF/view/InsertMember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -회원 가입 정보 요청 분석
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
		System.out.println("[InsertMemberController.doGet()] memberId : " + memberId);
		System.out.println("[InsertMemberController.doGet()] memberPw : " + memberPw);
		
		// -모델값(회원 가입 정보)을 반환하는 비즈니스 로직(모델) 호출
		MemberDao memberDao = new MemberDao();
		memberDao.insertMember(member);
		
		// -뷰 redirect
		response.sendRedirect(request.getContextPath() + "/LoginController");
	}
}
