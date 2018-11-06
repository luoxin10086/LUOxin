package com.news.model;

import java.io.Serializable;
/**
 * 
 * @author Administrator
 *	主题类
 */
public class Topic implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tid;
	private String tname;
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	
}
