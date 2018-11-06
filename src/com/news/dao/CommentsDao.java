package com.news.dao;

import java.sql.SQLException;
import java.util.List;

import com.news.model.Comment;

public interface CommentsDao {
	/**
	 * 根据新闻cnid获取评论
	 */
	public List<Comment> getComments(int cnid) throws SQLException;
	/**
	 * 删除一篇文章的所有评论
	 */
	public int delAllComments(int cnid) throws SQLException;
	/**
	 * 根据cid删除评论评论
	 */
	public int delComments(int cid) throws SQLException;
	/**
	 * 新增评论
	 */
	public int addComment(Comment comment)throws SQLException;
}
