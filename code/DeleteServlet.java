package custom.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.bean.CustomerBean;

@SuppressWarnings("serial")
@WebServlet(name = "DeleteServlet", urlPatterns="/delete")

public class DeleteServlet extends HttpServlet {

	@Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {


		 try {

			 String email = request.getParameter("email");

			 CustomerBean dab = new CustomerBean();
			 dab.deleteUserInfo(email);

			 response.sendRedirect(request.getContextPath() + "/findall");
		} catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }

    }

}