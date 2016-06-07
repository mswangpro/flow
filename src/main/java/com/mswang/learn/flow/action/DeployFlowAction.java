package com.mswang.learn.flow.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

@Action
@Scope("prototype")
@Namespace(value = "/")
@ParentPackage(value = "struts-default")
public class DeployFlowAction extends CommonAction{

	private static final Logger LOGGER = LoggerFactory.getLogger(DeployFlowAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -6563692396614441711L;
	
	@Resource(name="repositoryService")
	private RepositoryService repositoryService;
	
	public void depolyFlows () {
		this.deploy("", "请假流程");
	}
	public void deploy(String bpmnName, String flowName) {
        String processFilePath = this.getClass().getClassLoader().getResource("deployments/" + bpmnName + ".bpmn").getPath();
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(processFilePath);
            repositoryService.createDeployment().name(flowName).addInputStream(bpmnName + ".bpmn", inputStream).deploy();
            LOGGER.info("流程---"+flowName+"----部署成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
