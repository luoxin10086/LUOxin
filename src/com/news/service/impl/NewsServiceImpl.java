package com.news.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.news.util.JDBCUtil;

import com.news.dao.NewsDao;
import com.news.dao.impl.CommentsDaoImpl;
import com.news.dao.impl.NewsDaoImpl;
import com.news.model.Comment;
import com.news.model.News;
import com.news.model.Page;
import com.news.service.NewsService;

public class NewsServiceImpl implements NewsService {

    @Override
    public List<News> findAllNews() throws SQLException {
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();

            return new NewsDaoImpl(conn).getAllnews();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(conn, null, null);
        }
    }

    @Override
    public List<News> findAllNewsByTid(int tid) throws SQLException {
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();

            return new NewsDaoImpl(conn).getAllnewsByTID(tid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(conn, null, null);
        }
    }

    @Override
    public List<News> findAllNewsByTname(String tname) throws SQLException {
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();

            return new NewsDaoImpl(conn).getAllnewsByTname(tname);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(conn, null, null);
        }
    }

    @Override
    public List<News> findLatestNewsByTid(int tid, int limit)
            throws SQLException {
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();

            return new NewsDaoImpl(conn).getLatestNewsByTID(tid, limit);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(conn, null, null);
        }
    }

    @Override
    public List<List<News>> findLatestNewsByTid(Map<Integer, Integer> topicsMap)
            throws SQLException {
        Connection conn = null;
        try {
            List<List<News>> result = null;
            if (topicsMap != null && topicsMap.size() != 0) {
                conn = JDBCUtil.getConnection();
                NewsDao newsDao = new NewsDaoImpl(conn);

                result = new ArrayList<List<News>>();
                Iterator<Entry<Integer, Integer>> topics = topicsMap.entrySet()
                        .iterator();
                while (topics.hasNext()) {
                    Entry<Integer, Integer> oneTopic = topics.next();
                    result.add(newsDao.getLatestNewsByTID(oneTopic.getKey(),
                            oneTopic.getValue()));
                }
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(conn, null, null);
        }
    }

    @Override
    public News findNewsByNid(int nid) throws SQLException {
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();

            return new NewsDaoImpl(conn).getNewsByNID(nid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(conn, null, null);
        }
    }

    @Override
    public int deleteNews(int nid) throws SQLException {
        Connection conn = null;
        int result;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            // 删除相关评论
           new CommentsDaoImpl(conn).delAllComments(nid);
           System.out.println("删除评论");
          List<Comment> comments = new CommentsDaoImpl(conn).getComments(nid);
          System.out.println(comments.size());
            // 删除新闻
            result = new NewsDaoImpl(conn).deleteNews(nid);
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

    // 分页获取新闻
    public void findPageNews(int ntid, Page<News> pageObj) throws SQLException {
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            NewsDao newsDao = new NewsDaoImpl(conn);
            int totalCount = newsDao.getNewsCountByTID(ntid);
            pageObj.setTotalCount(totalCount); // 设置总数量并计算总页数
            if (totalCount > 0) {
            	//当前页大于总页数
                if (pageObj.getCurrPageNo() > pageObj.getTotalPageCount()){
                    pageObj.setCurrPageNo(pageObj.getTotalPageCount());
                }
                List<News> newsList = newsDao.getPageNewsList(ntid,
                        pageObj.getCurrPageNo(), pageObj.getPageSize());
                pageObj.setNewsList(newsList);
            } else {
                pageObj.setCurrPageNo(0);
                pageObj.setNewsList(new ArrayList<News>());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeAll(conn, null, null);
        }
    }

    @Override
    public int addNews(News news) throws SQLException {
        Connection conn = null;
        int result;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            // 添加新闻
            news.setNcreatedate(new Date());
            news.setNmodifydate(news.getNcreatedate());
            result = new NewsDaoImpl(conn).addNews(news);
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
    
    @Override
    public int modifyNews(News news) throws SQLException {
        Connection conn = null;
        int result;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            // 添加新闻
            news.setNmodifydate(new Date());
            if (!news.getNpicpath().contains(".")) {//是否包含点
            	System.out.println("没图片"+news.getNpicpath());
            	result = new NewsDaoImpl(conn).updateNews(news);
            	System.out.println(result);
			}else {
				System.out.println("有图片"+news.getNpicpath());
				result = new NewsDaoImpl(conn).updateNewsMap(news);
				System.out.println(result);
			}
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
