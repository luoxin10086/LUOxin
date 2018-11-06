package com.news.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.model.Topic;
import com.news.service.TopicService;
import com.news.service.impl.TopicServiceImpl;
@WebServlet("/topic.do")
public class TopicServlet extends HttpServlet{
	private TopicService topicService = new TopicServiceImpl();
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
		case "list":
			getAllTopics(req,resp);
			break;
		case "addtopic":{
			addTopic(req,resp);
			break;
		}	
		default:
			break;
		}
	}
	//新增主题
	public void addTopic(HttpServletRequest req, HttpServletResponse resp){
		try{
			System.out.println("addTopic");
			String tname = req.getParameter("tname");
			System.out.println("tname:"+tname);
			PrintWriter out = resp.getWriter();
			if(tname != null && !"".equals(tname)){
				//1.判断主题不重复
				if(topicService.getTopicByName(tname) > 0){//重复值
					out.println("主题不能重复");
				}else{
					//2.添加主题
					if(topicService.addTopic(tname)){
						//out.println("主题添加成功！");
						//重定向到编辑主题
						resp.sendRedirect("topic.do?opr=list");
					}else{
						out.println("主题添加失败！");
					}
				}
				
			}else{
				out.println("主题不能为空");
			}
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	//查询所有
	public void getAllTopics(HttpServletRequest req, HttpServletResponse resp){
		System.out.println("listAllTopics");
		List<Topic> list = topicService.getAll();
		//存值
		req.setAttribute("list", list);
		//转发
		try {
			req.getRequestDispatcher("/newspages/topic_list.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		};		
	}
}
