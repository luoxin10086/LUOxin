package com.news.model;

import java.util.List;

public class Page<E>{

	private int currPageNo = 1;//当前页数
	private int pageSize = 15;//每页记录条数
	private int totalCount;//记录总条数
	private int totalPageCount;//总页数
	private List<E> newsList;//每页新闻集合
	public int getCurrPageNo() {
		return currPageNo;
	}
	public void setCurrPageNo(int currPageNo) {
		this.currPageNo = currPageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		//总页数
		this.totalPageCount = (int) Math.ceil(totalCount*1.0/pageSize*1.0);
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public List<E> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<E> newsList) {
		this.newsList = newsList;
	}
	
}
