package com.news.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.news.dao.UserDao;
import com.news.dao.impl.UserDaoImpl;
import com.news.model.User;
import com.news.service.UserService;
import com.news.util.JDBCUtil;

public class UserServiceImpl implements UserService{

	@Override
	public User login(User user) {
		Connection conn = null;
		User newUser = null;
		try {
			conn = JDBCUtil.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			//查询方法
			newUser = userDao.getUserByName(user.getUname(), user.getUpwd());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.closeAll(conn, null, null);
		}		
		return newUser;
	}

}
