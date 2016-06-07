package com.mswang.learn.utils;

import java.util.Map;

public class SQLPageParamVo {

	private String sql;
	private String totalSQL;
	private Map<String, Object> param;
	private Integer pageIndex;
	private Integer pageSize = 20;
	private Integer total;
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getTotalSQL() {
		return totalSQL;
	}
	public void setTotalSQL(String totalSQL) {
		this.totalSQL = totalSQL;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
