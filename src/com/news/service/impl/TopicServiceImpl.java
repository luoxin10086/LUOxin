package com.news.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.news.dao.TopicDao;
import com.news.dao.impl.TopicDaoImpl;
import com.news.model.Topic;
import com.news.service.TopicService;
import com.news.util.JDBCUtil;

public class TopicServiceImpl implements TopicService{

	@Override
	public boolean addTopic(String tname) {
		boolean flag = false;
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
			TopicDao topicDao = new TopicDaoImpl(conn);
			//关闭事务
			conn.setAutoCommit(false);
			int num = topicDao.addTopic(tname);
			if(num == 1){
				flag = true;
				conn.commit();
			}else{
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			JDBCUtil.closeAll(conn, null, null);
		}
		return flag;
	}

	@Override
	public boolean delTopic(int tid) {
		
		return false;
	}

	@Override
	public boolean updateTopic(Topic topic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Topic> getAll() {
		List<Topic> list = null;
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
			TopicDao topicDao = new TopicDaoImpl(conn);
			list = topicDao.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.closeAll(conn, null, null);
		}
		return list;
	}

	@Override
	public int getTopicByName(String name) {
		int num = 0;
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
			TopicDao topicDao = new TopicDaoImpl(conn);
			num = topicDao.getTopicByName(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.closeAll(conn, null, null);
		}
		return num;
	}

}
