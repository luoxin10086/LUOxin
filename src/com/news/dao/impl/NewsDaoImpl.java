package com.news.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.news.util.JDBCUtil;

import com.news.dao.BaseDao;
import com.news.dao.NewsDao;
import com.news.model.News;

public class NewsDaoImpl extends BaseDao implements NewsDao {

    public NewsDaoImpl(Connection conn) {
        super(conn);
    }

    // 获取所有新闻
    public List<News> getAllnews() throws SQLException {
        List<News> list = new ArrayList<News>();
        ResultSet rs = null;
        String sql = "SELECT `nid`, `ntid`, `ntitle`, `nauthor`,"
                + " `ncreateDate`, `nsummary`, `tname`,npicPath FROM `NEWS`, `TOPIC`"
                + " WHERE `NEWS`.`ntid` = `TOPIC`.`tid`"
                + " ORDER BY `ncreateDate` DESC";
        try {
            rs = this.executeQuery(sql);
            News news = null;
            while (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                news.setNauthor(rs.getString("nauthor"));
                news.setNcreatedate(rs.getTimestamp("ncreateDate"));
                news.setNsummary(rs.getString("nsummary"));
                news.setNtname(rs.getString("tname"));
                news.setNpicpath(rs.getString("npicPath"));
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(null, null, rs);
        }
        return list;
    }

    // 获取某主题下的所有新闻
    public List<News> getAllnewsByTID(int tid) throws SQLException {
        List<News> list = new ArrayList<News>();
        ResultSet rs = null;
        String sql = "SELECT `nid`, `ntid`, `ntitle`, `nauthor`,"
                + " `ncreateDate`, `nsummary`, `tname` FROM `NEWS`, `TOPIC`"
                + " WHERE `NEWS`.`ntid` = `TOPIC`.`tid` AND `NEWS`.`ntid` = ?"
                + " ORDER BY `ncreateDate` DESC";
        try {
            rs = this.executeQuery(sql, tid);
            News news = null;
            while (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                news.setNauthor(rs.getString("nauthor"));
                news.setNcreatedate(rs.getTimestamp("ncreateDate"));
                news.setNsummary(rs.getString("nsummary"));
                news.setNtname(rs.getString("tname"));
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(null, null, rs);
        }
        return list;
    }

    // 获取某主题下的最新新闻
    public List<News> getLatestNewsByTID(int tid, int limit)
            throws SQLException {
        List<News> list = new ArrayList<News>();
        ResultSet rs = null;
        String sql = "SELECT `nid`, `ntid`, `ntitle` FROM `NEWS` WHERE"
                + " `ntid` = ? ORDER BY `ncreatedate` DESC LIMIT ?";
        try {
            rs = this.executeQuery(sql, tid, limit);
            News news = null;
            while (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(null, null, rs);
        }
        return list;
    }

    // 获取某主题下的新闻数量
    public int getNewsCountByTID(int tid) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT COUNT(`ntid`) FROM `news` WHERE `ntid` = ?";
        int count = -1;
        try {
            rs = this.executeQuery(sql, tid);
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(null, null, rs);
        }
        System.out.println(count);
        return count;
    }

    // 获取某条新闻
    public News getNewsByNID(int nid) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM `NEWS`, `TOPIC`"
                + " WHERE `NEWS`.`ntid` = `TOPIC`.`tid` AND `NEWS`.`nid` = ?"
                + " ORDER BY `ncreateDate` DESC";
        News news = null;
        try {
            rs = this.executeQuery(sql, nid);
            if (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                news.setNauthor(rs.getString("nauthor"));
                news.setNcreatedate(rs.getTimestamp("ncreateDate"));
                news.setNpicpath(rs.getString("npicPath"));
                news.setNcontent(rs.getString("ncontent"));
                news.setNmodifydate(rs.getTimestamp("nmodifyDate"));
                news.setNsummary(rs.getString("nsummary"));
                news.setNtname(rs.getString("tname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(null, null, rs);
        }
        return news;
    }

    public List<News> getAllnewsByTname(String tname) throws SQLException {
        List<News> list = new ArrayList<News>();
        ResultSet rs = null;
        // 获取某主题下的所有新闻
        String sql = "SELECT `nid`, `ntid`, `ntitle`, `nauthor`,"
                + " `ncreateDate`, `nsummary`, `tname` FROM `NEWS`, `TOPIC`"
                + " WHERE `NEWS`.`ntid` = `TOPIC`.`tid` AND `TOPIC`.`tname` = ?"
                + " ORDER BY `ncreateDate` DESC";
        try {
            rs = this.executeQuery(sql, tname);
            News news = null;
            while (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                news.setNauthor(rs.getString("nauthor"));
                news.setNcreatedate(rs.getTimestamp("ncreateDate"));
                news.setNsummary(rs.getString("nsummary"));
                news.setNtname(rs.getString("tname"));
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(null, null, rs);
        }
        return list;
    }

    // 删除某条新闻
    public int deleteNews(int nid) throws SQLException {
        String sql = "DELETE FROM `NEWS` WHERE `NID` = ?";
        int result = 0;
        try {
            result = executeUpdate(sql, nid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    // 获得所有新闻的数量
    public int getTotalCount() throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT COUNT(`nid`) FROM `news`";
        int count = -1;
        try {
            rs = this.executeQuery(sql);
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(null, null, rs);
        }
        return count;
    }

    // 分页获得新闻
    public List<News> getPageNewsList(int ntid, int pageNo, int pageSize)
            throws SQLException {
        List<News> list = new ArrayList<News>();
        ResultSet rs = null;
        String sql = "SELECT `nid`, `ntid`, `ntitle`, `nauthor`,"
                + " `ncreateDate`, `nsummary` FROM `NEWS`"
                + " WHERE `NEWS`.`ntid` = ?"
                + " ORDER BY `ncreateDate` DESC LIMIT ?, ?";
        try {
            rs = this.executeQuery(sql,ntid, (pageNo - 1) * pageSize, pageSize);
            News news = null;
            while (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                news.setNauthor(rs.getString("nauthor"));
                news.setNcreatedate(rs.getDate("ncreateDate"));
                news.setNsummary(rs.getString("nsummary"));
                list.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeAll(null, null, rs);
        }
        return list;
    }

    @Override
    public int addNews(News news) throws SQLException {
        String sql = "insert into news(NTID,NTITLE,NAUTHOR,NCONTENT,NSUMMARY,"
                + "NCREATEDATE,NMODIFYDATE,NPICPATH) values(?,?,?,?,?,?,?,?)";
        int result = 0;
        try {
        	System.out.println(news);
            result = executeUpdate(sql, new Object[] { news.getNtid(), news.getNtitle(),
                            news.getNauthor(), news.getNcontent(),
                            news.getNsummary(), news.getNcreatedate(),
                            news.getNmodifydate(), news.getNpicpath() });
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }
    
    @Override
    public int updateNews(News news) throws SQLException {
    	System.out.println(news.toString());
        String sql = "UPDATE news SET NTID=?,NTITLE=?,NAUTHOR=?,NCONTENT=?,NSUMMARY=?,"
                + "NMODIFYDATE=? where NID=?";
        int result = 0;
        try {
            result = executeUpdate(sql, new Object[] { news.getNtid(), news.getNtitle(),
                    news.getNauthor(), news.getNcontent(),
                    news.getNsummary(), news.getNmodifydate(),
                    news.getNid() });
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

	@Override
	public int updateNewsMap(News news) throws SQLException {
		System.out.println(news.toString());
        String sql = "UPDATE news SET NTID=?,NTITLE=?,NAUTHOR=?,NCONTENT=?,NSUMMARY=?,"
                + "NMODIFYDATE=?,NPICPATH=? where NID=?";
        int result = 0;
        try {
            result = executeUpdate(sql, new Object[] { news.getNtid(), news.getNtitle(),
                    news.getNauthor(), news.getNcontent(),
                    news.getNsummary(), news.getNmodifydate(),
                    news.getNpicpath(), news.getNid() });
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
	}
}
