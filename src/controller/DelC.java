/*
 * �û�����
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
		//���ܲ�������
		String type=request.getParameter("type");
		//����id
		String id=request.getParameter("id");
		//����UserService���ɾ��
		if("del".equals(type)) {
			if(userService.delUser(id)) {
				//forward
				request.getRequestDispatcher("/Ok").forward(request, response);
			}else {
				request.getRequestDispatcher("/Err").forward(request, response);
			}
		}else if("gotoUpDateV".equals(type)) {
			//Ҫȥ�޸Ľ���
			//��ȡuser����
			User user=userService.getUserById(id);
			//Ϊ������һservlet�����棩ʹ�����ǵ�user���󣬿ɰѸ�user����request��������
			request.setAttribute("user", user);
			request.getRequestDispatcher("/WEB-INF/UpdateUsers.jsp").forward(request, response);
			
		}else if("update".equals(type)) {
			//ȷ���޸�
			String Id=request.getParameter("Id");
			String userName=request.getParameter("userName");
			String Email=request.getParameter("Email");
			String Grade=request.getParameter("Grade");
			String pwd=request.getParameter("Pwd");
			
			//�ѽ��յ�����Ϣ����װ��User����
			User user=new User(Integer.parseInt(Id),Integer.parseInt(Grade),userName,pwd,Email);
			//�޸��û�
			if(userService.updUser(user)) {
				request.getRequestDispatcher("/Ok").forward(request, response);
			}else {
				request.getRequestDispatcher("/Err").forward(request, response);
			}
		}else if("gotoAddUser".equals(type)) {
			//��ת
			request.getRequestDispatcher("/gotoAddUserView").forward(request, response);
			
		}else if("addUser".equals(type)) {
			String userName=request.getParameter("userName");
			String Email=request.getParameter("Email");
			String Grade=request.getParameter("Grade");
			String pwd=request.getParameter("Pwd");
			//�ѽ��յ�����Ϣ����װ��User����
			User user=new User();
			user.setName(userName);
			user.setEmail(Email);
			user.setGrade(Integer.parseInt(Grade));
			user.setPwd(pwd);
			//����û�
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
