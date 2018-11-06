package com.news.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.news.dao.BaseDao;
import com.news.dao.UserDao;
import com.news.model.User;

public class UserDaoImpl extends BaseDao implements UserDao{

	public UserDaoImpl(Connection conn) {
		super(conn);
	}

	@Override
	public User getUserByName(String name, String password) throws SQLException {
		String sql = "select * from news_users where uname = ? and upwd=?;";
		ResultSet rs = executeQuery(sql,name,password);
		User user = null;
		while(rs.next()){
			user = new User();
			user.setUid(rs.getInt("uid"));
			user.setUname(rs.getString("uname"));
			user.setUpwd(rs.getString("upwd"));
			user.setUrole(rs.getInt("urole"));
		}
		return user;
	}

}
