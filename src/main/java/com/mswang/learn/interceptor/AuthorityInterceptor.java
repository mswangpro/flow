package com.mswang.learn.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptor extends AbstractInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8517094820174963864L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		 ActionContext actionContext = invocation.getInvocationContext();
		 Map<String,Object> session = actionContext.getSession();
		 HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		 HttpServletResponse respones = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
		 return invocation.invoke();
	}

}
