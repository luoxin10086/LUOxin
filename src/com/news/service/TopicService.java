package com.news.service;

import java.util.List;

import com.news.model.Topic;

public interface TopicService {
	//新增
	public boolean addTopic(String tname);
	//删除
	public boolean delTopic(int tid);
	//修改
	public boolean updateTopic(Topic topic);
	//查询所有
	public List<Topic> getAll();
	//通过名字查询
	public int getTopicByName(String name);
}
