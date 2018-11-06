package com.news.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.news.dao.BaseDao;
import com.news.dao.CommentsDao;
import com.news.model.Comment;

public class CommentsDaoImpl extends BaseDao implements CommentsDao{

	public CommentsDaoImpl(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	//根据新闻cnid获取评论
	@Override
	public List<Comment> getComments(int cnid) throws SQLException {
		List<Comment> cmenList = new ArrayList<Comment>();
		String sql = "SELECT cid,cnid,ccontent,cdate,cip,cauthor FROM comments where cnid = ?  order by cdate desc";
		ResultSet rs = executeQuery(sql,cnid);
		while(rs.next()){
			Comment comment = new Comment();
			comment.setCid(rs.getInt("cid"));
			comment.setCnid(rs.getInt("cnid"));
			comment.setCcontent(rs.getString("ccontent"));
			comment.setCdate(rs.getDate("cdate"));
			comment.setCip(rs.getString("cip"));
			comment.setCauthor(rs.getString("cauthor"));
			cmenList.add(comment);
		}
		return cmenList;
	}
	/**
	 * 删除一篇文章的所有评论
	 */
	@Override
	public int delAllComments(int cnid) throws SQLException {
		int num = 0;
		String sql = "delete from comments where cnid = ? ";
		Object[] params = new Object[1];
		params[0] = cnid;
		num = executeUpdate(sql, params);
		return num;
	}
	/**
	 * 根据cid删除评论评论
	 */
	@Override
	public int delComments(int cid) throws SQLException {
		int num = 0;
		String sql = "delete from comments where cid = ? ";
		Object[] params = new Object[1];
		params[0] = cid;
		num = executeUpdate(sql, params);
		return num;
	}
    // 添加评论
	@Override
	public int addComment(Comment comment) throws SQLException {
        String sql = "INSERT INTO `comments`(`CNID`, `CCONTENT`, `CDATE`," +
        		"`CIP`,`CAUTHOR`) VALUES(?, ?, ?, ?, ?)";
        System.out.println(comment.getCnid() + ":" + comment.getCcontent()
        + ":" + comment.getCdate() + ":" + comment.getCip() + ":"
        + comment.getCauthor());
        Object[] params = new Object[]{comment.getCnid(),comment.getCcontent(),comment.getCdate(),
        		comment.getCip(),comment.getCauthor()};
        int result = 0;
        try {
        	result = executeUpdate(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
}
