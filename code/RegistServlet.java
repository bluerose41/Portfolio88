package custom.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.bean.CustomerBean;
import custom.bean.UserInfo;
import custom.errors.DuplicateEmailException;



@SuppressWarnings("serial")
@WebServlet(name ="RegistServlet",urlPatterns="/regist" )

public class RegistServlet extends HttpServlet {

	@Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {


			try {

				String name = request.getParameter("name");
				String yomi = request.getParameter("yomi");
				String birthday = request.getParameter("birthday");
				String zip = request.getParameter("zip");
				String address = request.getParameter("address");
				String tel = request.getParameter("tel");
				String email = request.getParameter("email");

				if(name == null || name.length() < 1){
					request.setAttribute("message", "名前を入力してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/regist.jsp");
					rd.forward(request, response);
					return;
				}

				if(birthday == null || birthday.length() < 1){
					request.setAttribute("message", "生年月日を入力してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/regist.jsp");
					rd.forward(request, response);
					return;
				}

				if(address == null || address.length() < 1){
					request.setAttribute("message", "住所を入力してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/regist.jsp");
					rd.forward(request, response);
					return;
				}

				if(tel == null || tel.length() < 1){
					request.setAttribute("message", "電話番号を入力してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/regist.jsp");
					rd.forward(request, response);
					return;
				}

				if(email == null || email.length() <1){
					request.setAttribute("message", "メールアドレスを入力してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/regist.jsp");
					rd.forward(request, response);
					return;
				}

				UserInfo userInfo = new UserInfo();

				userInfo.setName(name);
				userInfo.setYomi(yomi);
				userInfo.setBirthday(birthday);
				userInfo.setZip(zip);
				userInfo.setAddress(address);
				userInfo.setTel(tel);
				userInfo.setEmail(email);

				CustomerBean dab = new CustomerBean();

				dab.registUserInfo(userInfo);

				response.sendRedirect(request.getContextPath()+"/findall");

			} catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,
                    response);

			} catch (DuplicateEmailException e) {
				request.setAttribute("message", "このメールアドレスは既に登録されています");
				RequestDispatcher rd = request.getRequestDispatcher("/regist.jsp");
				rd.forward(request, response);

			}

    }

}