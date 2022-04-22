package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CashBookDao;
import vo.CashBook;

@WebServlet("/CashBookOneController")
public class CashBookOneController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -로그인 여부
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId == null) {
			// 로그인 되지 않은 경우
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
		
		// 1) 해당 링크 가계부 리스트 상세보기 요청 분석
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		System.out.println("[CashBookOneController.doGet] cashbookNo : " + cashbookNo);
		
		// 2) 모델값(해당 링크 가계부 리스트 상세보기)을 반환하는 비즈니스 로직(모델) 호출
		CashBookDao cashbookDao = new CashBookDao();
		CashBook cashbook = cashbookDao.selectCashBookOne(cashbookNo);
		
		// -해당 링크 상세보기 출력에 필요한 모델값
		request.setAttribute("cashbookNo", cashbook.getCashbookNo());
		request.setAttribute("cashDate", cashbook.getCashDate());
		request.setAttribute("kind", cashbook.getKind());
		request.setAttribute("cash", cashbook.getCash());
		request.setAttribute("memo", cashbook.getMemo());
		request.setAttribute("updateDate", cashbook.getUpdateDate());
		request.setAttribute("createDate", cashbook.getCreatDate());
		
		// 3) 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/CashBookOne.jsp").forward(request, response);
	}
}
