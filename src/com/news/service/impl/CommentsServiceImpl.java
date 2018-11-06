package com.news.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.news.dao.CommentsDao;
import com.news.dao.impl.CommentsDaoImpl;
import com.news.model.Comment;
import com.news.service.CommentsService;
import com.news.util.JDBCUtil;

public class CommentsServiceImpl implements CommentsService{
	/**
	 * 根据cid删除评论
	 */
	@Override
	public int delComments(int cid) throws SQLException {
		Connection conn = null;
		int num = 0;
		try {
			conn = JDBCUtil.getConnection();
			CommentsDao commentsDao = new CommentsDaoImpl(conn);
			num = commentsDao.delComments(cid);
				System.out.println("评论删除成功"+num);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.closeAll(conn, null, null);
		}
		return num;
	}

	@Override
	public List<Comment> findCommentsByNid(int nid) throws SQLException {
			Connection conn = null;
			try {
				conn = JDBCUtil.getConnection();
				
				return new CommentsDaoImpl(conn).getComments(nid);
			} catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	        } finally {
	            JDBCUtil.closeAll(conn, null, null);
	        }
	}

	@Override
	public int addComment(Comment comment) throws SQLException {
		  Connection conn = null;
	        int result;
	        try {
	            conn = JDBCUtil.getConnection();
	            conn.setAutoCommit(false);

	            result = new CommentsDaoImpl(conn).addComment(comment);

	            conn.commit();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            if (conn != null)
	                try {
	                    conn.rollback();
	                } catch (SQLException e1) {
	                    e1.printStackTrace();
	                }
	            throw e;
	        } finally {
	            JDBCUtil.closeAll(conn, null, null);
	        }
	        return result;
	}

}
