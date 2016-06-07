package com.mswang.learn.flow.service.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlowServiceImpl implements FlowService {
	/**
	 * 注入流程需要的service
	 * */
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ProcessEngineFactoryBean processEngine;
	@Override
	public void startProcess (String processDefinitionKey,String businessId,Map<String, Object> variables) {
		runtimeService.startProcessInstanceByKey(processDefinitionKey,businessId,variables);
	}
	@Override
	public void completeTask (String businessId,Map<String, Object> variables) {
		//根据businessId查询任务   重点说明：这里的方法只适合===单例流程
		Execution execution = runtimeService.createExecutionQuery().processInstanceBusinessKey(businessId, false).singleResult();
		Task task = taskService.createTaskQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
		//完成任务
		taskService.complete(task.getId(), variables);
		//注意
	    //（1）如果是单例流程，执行对象ID就是流程实例ID
	    //（2）如果一个流程有分支和聚合，那么执行对象ID和流程实例ID就不相同
	    //（3）一个流程中，流程实例只有1个，执行对象可以存在多个。
	}
	
	@Override
	public InputStream showProcessImg (String processDefinitionKey) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
		return repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
	}
	
	@Override
	public void showTaskProcessImg (String businessId) {
		String businessID = "mswang123123";
		List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessID).list();
	        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
	            List<String> pobjHightLightNodes = new ArrayList<String>();
	            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(historicProcessInstance.getId()).list();
	            BpmnModel bpmnModel = null;
	            for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
	                pobjHightLightNodes.add(historicActivityInstance.getActivityId());
	                bpmnModel = repositoryService.getBpmnModel(historicActivityInstance.getProcessDefinitionId());
	            }

	            // 使用spring注入引擎请使用下面的这行代码
	            Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());

	            InputStream inputStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", pobjHightLightNodes);
	            File file = new File("D:/flow.png");
	    		try {
					FileUtils.copyInputStreamToFile(inputStream, file);
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	}
	
	
}
