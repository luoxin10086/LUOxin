package com.news.test;

import java.sql.Connection;
import java.sql.SQLException;

import com.news.dao.UserDao;
import com.news.dao.impl.UserDaoImpl;
import com.news.model.User;
import com.news.util.JDBCUtil;
//模拟service
public class UserDaoTest {

	public static void main(String[] args) {
		Connection conn;
		try {
			conn = JDBCUtil.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			//查询
			String name = "admin11";
			String pwd = "admin";
			User user = userDao.getUserByName(name, pwd);
			if(user != null){
				System.out.println(user.getUname()+"|"+user.getUrole());
			}else{
				System.out.println("登录失败！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
