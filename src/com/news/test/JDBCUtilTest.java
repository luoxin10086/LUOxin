package com.news.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.news.util.JDBCUtil;

public class JDBCUtilTest {

	public static void main(String[] args) {
		try { 
			Connection conn = JDBCUtil.getConnection();
			System.out.println(conn);
			PreparedStatement pstmt = conn.prepareStatement("select * from news_users");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString("uname"));
			}
			System.out.println("连接成功");
			JDBCUtil.closeAll(conn, pstmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
