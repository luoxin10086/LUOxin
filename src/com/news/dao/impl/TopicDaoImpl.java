package com.news.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.news.dao.BaseDao;
import com.news.dao.TopicDao;
import com.news.model.Topic;

public class TopicDaoImpl extends BaseDao implements TopicDao{

	public TopicDaoImpl(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int addTopic(String tname) {
		String sql = "INSERT INTO topic(tname) VALUE(?)";
		int num = 0;
		try {
			num = super.executeUpdate(sql, tname);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public int getTopicByName(String tname) {
		String sql = "SELECT COUNT(*) AS num FROM topic WHERE tname=?;";
		int num = 0;
		try {
			ResultSet rs = super.executeQuery(sql, tname);
			while(rs.next()){
				num = rs.getInt("num");
			}
			System.out.println("dao:num:"+num);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public int deleteById(int tid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumsById(int tid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateTopic(Topic topic) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Topic> getAll() throws SQLException {
		List<Topic> list = new ArrayList<Topic>();
		String sql = "select tid,tname from topic;";
		ResultSet rs = super.executeQuery(sql);
		while(rs.next()){
			Topic topic = new Topic(); 
			topic.setTid(rs.getInt("tid"));
			topic.setTname(rs.getString("tname"));
			list.add(topic);
		}
		return list;
	}

}
