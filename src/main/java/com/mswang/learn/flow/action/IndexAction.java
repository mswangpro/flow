package com.mswang.learn.flow.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@ParentPackage(value = "struts-default-authority")
@Namespace(value = "/")
@Results({ @Result(name = "toPage", location = "/"), @Result(name = "toaddOrUpdate", location = "/") })
public class IndexAction extends CommonAction{

}
