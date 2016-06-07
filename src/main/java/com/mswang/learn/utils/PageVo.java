package com.mswang.learn.utils;

import java.util.List;

public class PageVo<T> {

	private List<T> data;
	private long total;
	private long pageIndex;
	private int pageSize = 20;
	private int totalPages;
	private int currentPageSize;
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(long pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getCurrentPageSize() {
		return currentPageSize;
	}
	public void setCurrentPageSize(int currentPageSize) {
		this.currentPageSize = currentPageSize;
	}
	
	
}
