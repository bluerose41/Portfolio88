package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBmanager;
import dto.Shoutdto;
import dto.Userdto;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		RequestDispatcher dispatcher = null;
		String message = null;

		if (loginId.equals("") || password.equals("")) {
			
			message = "ログインIDとパスワードは必須入力です";

			request.setAttribute("alert", message);

			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		} else {
			
			DBmanager dbm = new DBmanager();
			Userdto user = dbm.getLoginUser(loginId, password);

			if (user != null) {
				
				ArrayList<Shoutdto> list = dbm.getShoutList();
				HttpSession session = request.getSession();

				session.setAttribute("user", user);
				session.setAttribute("shouts", list);

				dispatcher = request.getRequestDispatcher("top.jsp");
			} else {
				
				message = "ログインIDまたはパスワードが違います";
				request.setAttribute("alert", message);

				dispatcher = request.getRequestDispatcher("index.jsp");
			}

			dispatcher.forward(request, response);
		}
	}
}
