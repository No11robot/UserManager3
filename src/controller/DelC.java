/*
 * 用户处理
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.UserService;

@WebServlet("/DelC")
public class DelC extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DelC() {
        super();
        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		UserService userService=new UserService();
		//接受操作类型
		String type=request.getParameter("type");
		//接受id
		String id=request.getParameter("id");
		//调用UserService完成删除
		if("del".equals(type)) {
			if(userService.delUser(id)) {
				//forward
				request.getRequestDispatcher("/Ok").forward(request, response);
			}else {
				request.getRequestDispatcher("/Err").forward(request, response);
			}
		}else if("gotoUpDateV".equals(type)) {
			//要去修改界面
			//获取user对象
			User user=userService.getUserById(id);
			//为了让下一servlet（界面）使用我们的user对象，可把该user放入request对象域中
			request.setAttribute("user", user);
			request.getRequestDispatcher("/WEB-INF/UpdateUsers.jsp").forward(request, response);
			
		}else if("update".equals(type)) {
			//确定修改
			String Id=request.getParameter("Id");
			String userName=request.getParameter("userName");
			String Email=request.getParameter("Email");
			String Grade=request.getParameter("Grade");
			String pwd=request.getParameter("Pwd");
			
			//把接收到的信息，封装成User对象
			User user=new User(Integer.parseInt(Id),Integer.parseInt(Grade),userName,pwd,Email);
			//修改用户
			if(userService.updUser(user)) {
				request.getRequestDispatcher("/Ok").forward(request, response);
			}else {
				request.getRequestDispatcher("/Err").forward(request, response);
			}
		}else if("gotoAddUser".equals(type)) {
			//跳转
			request.getRequestDispatcher("/gotoAddUserView").forward(request, response);
			
		}else if("addUser".equals(type)) {
			String userName=request.getParameter("userName");
			String Email=request.getParameter("Email");
			String Grade=request.getParameter("Grade");
			String pwd=request.getParameter("Pwd");
			//把接收到的信息，封装成User对象
			User user=new User();
			user.setName(userName);
			user.setEmail(Email);
			user.setGrade(Integer.parseInt(Grade));
			user.setPwd(pwd);
			//添加用户
			if(userService.addUser(user)) {
				request.getRequestDispatcher("/Ok").forward(request, response);
			}else {
				request.getRequestDispatcher("/Err").forward(request, response);
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
