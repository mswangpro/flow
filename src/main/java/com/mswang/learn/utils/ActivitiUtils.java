package com.mswang.learn.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ActivitiUtils {
	private static ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config/activit_utils.xml").buildProcessEngine();
	@Test
	public void deploy () {
		Deployment deployment = processEngine.getRepositoryService()
		.createDeployment()
		.name("请假审批流程")
		.addClasspathResource("deploys/LeaveFlow.bpmn")
		.addClasspathResource("deploys/LeaveFlow.png")
		.deploy();
		System.out.println(deployment.getId()+"-------------"+deployment.getName());
		//act_re_deployment：部署对象表
		//act_re_procdef：流程定义表
		//act_ge_bytearray：资源文件表
		//act_ge_property：主键生成策略表

	}
	@Test
	public void startFlow () {
		Map<String, Object> variables = new HashMap<String, Object>();
		String businessId = "mswang123123";
	    ProcessInstance processInstance = processEngine.getRuntimeService()
		.startProcessInstanceByKey("LeaveFlow",businessId,variables);
	    System.out.println("流程实例id="+processInstance.getId()+",流程定义id="+processInstance.getProcessDefinitionId());
	    //流程实例id=2501,流程定义id=LeaveFlow:1:4
	    //act_ru_execution  
	    //act_ru_task
	}
	@Test
	public void findTask () {
		//根据businessId查询任务   重点说明：这里的方法只适合===单例流程
		Execution execution = processEngine.getRuntimeService().createExecutionQuery().processInstanceBusinessKey("mswang123123", false).singleResult();
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
		//完成任务
		Map<String, Object> variables = new HashMap<String, Object>();
		processEngine.getTaskService().complete(task.getId(), variables);
		//注意
	    //（1）如果是单例流程，执行对象ID就是流程实例ID
	    //（2）如果一个流程有分支和聚合，那么执行对象ID和流程实例ID就不相同
	    //（3）一个流程中，流程实例只有1个，执行对象可以存在多个。
	}
	@Test
	public void viewProcessImg () throws IOException {
		ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
		.processDefinitionKey("LeaveFlow").latestVersion().singleResult();
		InputStream inputStream = processEngine.getRepositoryService().getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
		File file = new File("D:/flow.png");
		FileUtils.copyInputStreamToFile(inputStream, file);
	}
}
