package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;
import vo.Cashbook;

@WebServlet("/InsertCashBookController")
public class InsertCashBookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// -요청분석은 view가 아닌 controller에서 하는 것이 좋다
		String y = request.getParameter("y");
		String m = request.getParameter("m");
		String d = request.getParameter("d");
		String cashDate = y + "-" + m + "-" + d;
		request.setAttribute("cashDate", cashDate);
		request.getRequestDispatcher("/WEB-INF/view/insertCashBookForm.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		String cashDate = request.getParameter("cashDate");
		String kind = request.getParameter("kind");
		int cash = Integer.parseInt(request.getParameter("cash"));
		String memo = request.getParameter("memo");
		
		System.out.println("[InsertCashBookController.doPost()] cashDate : " + cashDate);
		System.out.println("[InsertCashBookController.doPost()] kind : " + kind);
		System.out.println("[InsertCashBookController.doPost()] cash : " + cash);
		System.out.println("[InsertCashBookController.doPost()] memo : " + memo);
		
		Cashbook cashbook = new Cashbook();
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
		System.out.println("[InsertCashBookController.doPost()] hashtag.size() : " + hashtag.size());
		for(String h : hashtag) {
			System.out.println("[InsertCashBookController.doPost()] h : " + h);
		}
		
		CashbookDao cashbookDao = new CashbookDao();
		cashbookDao.insertCashbook(cashbook, hashtag);
		
		response.sendRedirect(request.getContextPath() + "/CashBookListByMonthController");
	}
}
