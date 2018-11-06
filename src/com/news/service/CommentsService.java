package com.news.service;

import java.sql.SQLException;
import java.util.List;

import com.news.model.Comment;

public interface CommentsService {
	/**
	 * 根据cid删除评论
	 */
	public int delComments(int cid) throws SQLException;
	/**
	 * 获取一篇文章的所有评论
	 */
	public List<Comment> findCommentsByNid(int nid) throws SQLException;
	/**
	 * 添加评论
	 */
	 public int addComment(Comment comment) throws SQLException;
}
