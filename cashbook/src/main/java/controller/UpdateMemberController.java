package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import vo.Member;

@WebServlet("/UpdateMemberController")
public class UpdateMemberController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -뷰 forward
		request.getRequestDispatcher("/WEB-INF/view/UpdateMember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		// -회원 정보 요청 분석
		String memberId = null;
		String memberPw = null;
		String memberName = null;
		String memberGender = null;
		String memberPhone1 = request.getParameter("memberPhone1");
		String memberPhone2 = request.getParameter("memberPhone2");
		String memberPhone3 = request.getParameter("memberPhone3");
		String memberEmail1 = request.getParameter("memberEmail1");
		String memberEmail2 = request.getParameter("memberEmail2");
		String memberBirth = null;
		String memberAddr = null;
		String originalMemberPw = null;
		
		Member member = new Member();
		if(request.getParameter("memberId") != null) {
			memberId = request.getParameter("memberId");
			member.setMemberId(memberId);
		}
		if(request.getParameter("memberPw") != null) {
			memberPw = request.getParameter("memberPw");
			member.setMemberPw(memberPw);			
		}
		if(request.getParameter("memberName") != null) {
			memberName = request.getParameter("memberName");
			member.setMemberName(memberName);			
		}
		if(request.getParameter("memberGender") != null) {
			memberGender = request.getParameter("memberGender");
			member.setMemberGender(memberGender);			
		}
//		if(request.getParameter("memberPhone1") != null && request.getParameter("memberPhone2") && request.getParameter("memberPhone3") != null) {
//			memberPhone = request.getParameter("memberPhone");
//			member.setMemberPhone(memberPhone);			
//		}
//		if(request.getParameter("memberEmail") != null) {
//			memberEmail = request.getParameter("memberEmail");
//			member.setMemberEmail(memberEmail);			
//		}
		if(request.getParameter("memberBirth") != null) {
			memberBirth = request.getParameter("memberBirth");
			member.setMemberBirth(memberBirth);			
		}
		if(request.getParameter("memberAddr") != null) {
			memberAddr = request.getParameter("memberAddr");
			member.setMemberAddr(memberAddr);			
		}
		String memberPhone = memberPhone1 + "-" + memberPhone2 + "-" + memberPhone3;
		member.setMemberPhone(memberPhone);	
		String memberEmail = memberEmail1 + "@" + memberEmail2;
		member.setMemberEmail(memberEmail);
		if(request.getParameter("originalMemberPw") != null) {
			originalMemberPw = request.getParameter("originalMemberPw");
		}
		
		System.out.println("[UpdateMemberController.doPost()] memberId : " + memberId);
		System.out.println("[UpdateMemberController.doPost()] memberPw : " + memberPw);
		System.out.println("[UpdateMemberController.doPost()] memberName : " + memberName);
		System.out.println("[UpdateMemberController.doPost()] memberGender : " + memberGender);
		System.out.println("[UpdateMemberController.doPost()] memberPhone : " + memberPhone);
		System.out.println("[UpdateMemberController.doPost()] memberBirth : " + memberBirth);
		System.out.println("[UpdateMemberController.doPost()] memberEmail : " + memberEmail);
		System.out.println("[UpdateMemberController.doPost()] memberAddr : " + memberAddr);
		System.out.println("[UpdateMemberController.doPost()] originalMemberPw : " + originalMemberPw);
		
		// -모델값(회원 가입 정보)을 반환하는 비즈니스 로직(모델) 호출
		MemberDao memberDao = new MemberDao();
		memberDao.updateMember(member, originalMemberPw);
		
		// -뷰 redirect
		response.sendRedirect(request.getContextPath() + "/SelectMemberOneController");
	}

}
