package com.news.dao;

import java.sql.SQLException;
import java.util.List;

import com.news.model.Topic;

public interface TopicDao {
	//新增标题
	public int addTopic(String tname) throws SQLException;
	//通过名字查找标题数量
	public int getTopicByName(String tname) throws SQLException;	
	
	//删除主题
	public int deleteById(int tid) throws SQLException;
	//判断主题是否被使用
	public int getNumsById(int tid) throws SQLException;
	
	//修改主题
	public int updateTopic(Topic topic) throws SQLException;
	
	//查询所有主题
	public List<Topic> getAll() throws SQLException;	
}
