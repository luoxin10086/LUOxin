package com.news.dao;
import java.sql.SQLException;

import com.news.model.User;
public interface UserDao {
	//账号和密码查询
	public User getUserByName(String name,String password) throws SQLException;
}
