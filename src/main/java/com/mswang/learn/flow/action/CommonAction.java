package com.mswang.learn.flow.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mswang.learn.utils.DateFormat;
import com.opensymphony.xwork2.ActionSupport;

public class CommonAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware{

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonAction.class);
	
	protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Map<String, Object> session;
    
    protected Integer limit;
    protected Integer pageCount;
    protected Long pageIndex;
    protected Integer pageSize;
    protected Long total;
    protected String sortOrder;
    protected String sortField;
    protected String id;
    protected String ids;
    
    protected void sendResponseMsg(String message) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;

        try {
            writer = response.getWriter();
            writer.write(message);
            writer.flush();
        } catch (IOException var8) {
            LOGGER.error("Json convert send to page error!", var8);
        } finally {
            if(null != writer) {
                writer.close();
                writer = null;
            }

        }
    }
    
    protected <T> void sendMsg(boolean success, T data, String msg) {
        HashMap<String, Object> responseMap = Maps.newHashMap();
        responseMap.put("flag", Boolean.valueOf(success));
        responseMap.put("data", data);
        responseMap.put("message", msg);
        this.sendResponseMsg(ObjGsonToJson(responseMap));
    }
    protected <T> void sendSuccessMsg() {
        this.sendMsg(true, "", "");
    }
    protected String ObjGsonToJson (Object obj) {
        Gson g = new GsonBuilder().setDateFormat(DateFormat.DATETIME24.getFormat()).create();
        return g.toJson(obj);
    }
    
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Long getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Long pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
