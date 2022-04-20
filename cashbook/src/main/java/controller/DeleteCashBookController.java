package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashBookDao;

@WebServlet("/DeleteCashBookController")
public class DeleteCashBookController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cashbookNo = 0;
		if(request.getParameter("cashbookNo") != null) {
			cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		}
		System.out.println("[DeleteCashBookController.doGet] cashbookNo : " + cashbookNo);
		
		CashBookDao cashbookDao = new CashBookDao();
		cashbookDao.deleteCashBook(cashbookNo);
		
		// -뷰 redirect
		response.sendRedirect(request.getContextPath() + "/CashBookListByMonthController");
	}
}
