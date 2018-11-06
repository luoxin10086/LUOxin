package com.news.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.model.User;
import com.news.service.UserService;
import com.news.service.impl.UserServiceImpl;

@WebServlet("/user.do")
public class UserServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.请求编码
		req.setCharacterEncoding("UTF-8");
		//2.响应格式
		resp.setContentType("text/html;charset=utf-8");
		//3.接受参数
		String opr = req.getParameter("opr");
		switch (opr) {
		case "login":
			login(req,resp);
			break;
		default:
			break;
		}
	}
	
	//登录
	public void login(HttpServletRequest req, HttpServletResponse resp){
		String uname = req.getParameter("uname");
		String upwd = req.getParameter("upwd");
		UserService service = new UserServiceImpl();
		User u = new User();
		u.setUname(uname);
		u.setUpwd(upwd);
		User user = service.login(u);
		try{
			if(user == null){//登录失败
				resp.sendRedirect("index.jsp");
			}else{//登录成功
				//session
				resp.sendRedirect("newspages/admin.jsp");
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
