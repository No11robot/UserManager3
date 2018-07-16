package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;
import service.UserService;

@WebServlet(name="LoginC",urlPatterns="/LoginC")
public class LoginC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginC() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String username= request.getParameter("username");
		String pwd=request.getParameter("password");
		//����UserService������ɵ����ݿ����֤
		UserService userService=new UserService();
		User user=new User();
		user.setName(username);
		user.setPwd(pwd);
		if(userService.checkUser(user)) {
			//��user���󱣴浽session��
			HttpSession session=request.getSession();
			session.setAttribute("login", user);
			request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
		}else {				
			request.setAttribute("err", "�û������ڻ�������󣡣���");
			request.getRequestDispatcher("/Login").forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
