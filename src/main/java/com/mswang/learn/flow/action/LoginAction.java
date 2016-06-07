package com.mswang.learn.flow.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.mswang.learn.flow.service.common.FlowService;
import com.mswang.learn.flow.service.user.UserService;
import com.mswang.learn.flow.vo.User;


@Scope("prototype")
@Namespace(value = "/")
@ParentPackage(value = "struts-default")
@Results({ @Result(name = "login", location = "/WEB-INF/jsp/login.jsp") })
@Action
public class LoginAction extends CommonAction{

	private User entity;
	@Autowired
	private UserService userService;
	@Autowired
	private FlowService flowService;
	List<User> hahas;
	public String login () {
		return "login";
	}
	public String logout () {
		return "login";
	}
	public void loginSubmit () {
		flowService.showTaskProcessImg("");
		//sendResponseMsg(ObjGsonToJson(userService.findUser()));
	}
	public User getEntity() {
		return entity;
	}
	public void setEntity(User entity) {
		this.entity = entity;
	}
	public List<User> getHahas() {
		return hahas;
	}
	public void setHahas(List<User> hahas) {
		this.hahas = hahas;
	}
	
	
}
