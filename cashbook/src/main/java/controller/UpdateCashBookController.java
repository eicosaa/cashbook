package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CashBookDao;
import vo.CashBook;

@WebServlet("/UpdateCashBookController")
public class UpdateCashBookController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -로그인 유/무에 따라 접근 허가
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId == null) {
			// 로그인 되지 않은 경우
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
				
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		System.out.println("[UpdateCashBookController] cashbookNo : " + cashbookNo);
		
		CashBookDao cashbookDao = new CashBookDao();
		CashBook cashbook = new CashBook();
		cashbook = cashbookDao.selectCashBookOne(cashbookNo); 
		
		request.setAttribute("cashDate", cashbook.getCashDate());
		request.setAttribute("kind", cashbook.getKind());
		request.setAttribute("cash", cashbook.getCash());
		request.setAttribute("memo", cashbook.getMemo());
		
		// -뷰 forward
		request.getRequestDispatcher("/WEB-INF/view/UpdateCashBook.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		String cashDate = request.getParameter("cashDate");
		String kind = request.getParameter("kind");
		int cash = Integer.parseInt(request.getParameter("cash"));
		String memo = request.getParameter("memo");
		
		System.out.println("[UpdateCashBookController.doPost()] cashDate : " + cashDate);
		System.out.println("[UpdateCashBookController.doPost()] kind : " + kind);
		System.out.println("[UpdateCashBookController.doPost()] cash : " + cash);
		System.out.println("[UpdateCashBookController.doPost()] memo : " + memo);
		
		CashBook cashbook = new CashBook();
		cashbook.setCashDate(cashDate);
		cashbook.setKind(kind);
		cashbook.setCash(cash);
		cashbook.setMemo(memo);
		
		// -hashtag 처리 (dao는 db를 사용하는 model이기에 db가 필요없는 model이 없으므로 일단 controller에서 처리)
		List<String> hashtag = new ArrayList<>();
		memo = memo.replace("#", " #"); // -자바의 String은 수정이 안되는 정적, 불변, 새로운 문자열 생성
		String[] arr = memo.split(" "); // -바뀐 memo를 공백문자로 자른다.
		for(String s : arr) {
			if(s.startsWith("#")) { // -#으로 시작할 때
				String temp = s.replace("#", ""); // -#문자를 공백으로 바꾼다.
				if(temp.length() > 0) { // -temp의 길이가 0보다 큰, 한자 이상일 떄
					hashtag.add(temp);
				}
			}
		}
		
		System.out.println("[UpdateCashBookController.doPost()] hashtag.size() : " + hashtag.size());
		for(String h : hashtag) {
			System.out.println("[UpdateCashBookController.doPost()] h : " + h);
		}
		
		CashBookDao cashbookDao = new CashBookDao();
		cashbookDao.updateCashBook(cashbook, hashtag);
		
		response.sendRedirect(request.getContextPath() + "/CashBookListByMonthController");
	}
}
