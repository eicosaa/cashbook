package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateCashBookController")
public class UpdateCashBookController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		
		// -뷰 forward
		request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/view/UpdateCashBook.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
