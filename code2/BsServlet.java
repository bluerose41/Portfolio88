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

@WebServlet("/bbs")
public class BsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBmanager dbm;	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String writing = request.getParameter("shout");
		RequestDispatcher dispatcher;

		
		if (!writing.equals("")) {
			HttpSession session = request.getSession();
			
			Userdto user = (Userdto) session.getAttribute("user");

			
			if(dbm == null){
				dbm = new DBmanager();
			}

			
			dbm.setWriting(user, writing);

			
			ArrayList<Shoutdto> list = dbm.getShoutList();

			
			session.setAttribute("shouts", list);
		}

		dispatcher = request.getRequestDispatcher("top.jsp");
		dispatcher.forward(request, response);
	}
}
