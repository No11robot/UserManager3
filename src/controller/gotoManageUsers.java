package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;

@WebServlet(name="gotoManageUsers",urlPatterns="/gotoManageUsers")
public class gotoManageUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public gotoManageUsers() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		//接受用户传入pageNow
		int pageNow=1;
		String s_pageNow=request.getParameter("pageNow");
		//判断s_pageNow
		if(s_pageNow!=null) {
			pageNow=Integer.parseInt(s_pageNow);
		}
		//调用servlet准备数据
		UserService userService=new UserService();
		ArrayList al=userService.getUserByPage(pageNow, 3);
		int pageCount=userService.getPageCount(3);
		request.setAttribute("al", al);
		request.setAttribute("pagecount", pageCount);
		//跳出
		request.getRequestDispatcher("/WEB-INF/manageUsers.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
