package com.news.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.news.model.Comment;
import com.news.service.CommentsService;
import com.news.service.impl.CommentsServiceImpl;
@WebServlet("/comment.do")
public class CommensServlet extends HttpServlet{
	private CommentsService commentsService = new CommentsServiceImpl();
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
		case "addComment":
			addComment(req,resp);
			break;
		default:
			break;
		}
	}
	public void addComment(HttpServletRequest req,HttpServletResponse resp){
		System.out.println("addComment");
		try {
		String cauthor = req.getParameter("cauthor");
		String cnid = req.getParameter("nid");
		String cip = req.getParameter("cip");
		String ccontent = req.getParameter("ccontent");
		Comment comment = new Comment();
		comment.setCauthor(cauthor);
		comment.setCnid(Integer.parseInt(cnid));
		comment.setCip(cip);
		comment.setCcontent(ccontent);
		comment.setCdate(new Date());
			if (commentsService.addComment(comment) != 1) {
				comment = null;
			}
			PrintWriter out = resp.getWriter();
			out.write(JSON.toJSONString(comment));
			out.close();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
