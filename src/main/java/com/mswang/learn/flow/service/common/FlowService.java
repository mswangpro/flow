package com.mswang.learn.flow.service.common;

import java.io.InputStream;
import java.util.Map;

public interface FlowService {

	/**
	 * 启动流程实例
	 * processDefinitionKey：流程定义的key值
	 * businessId：业务id
	 * variables：流程变量
	 * */
	public abstract void startProcess(String processDefinitionKey,
			String businessId, Map<String, Object> variables);

	/**
	 * 完成任务
	 * businessId：业务id
	 * assignee：任务办理人的id
	 * variables：流程变量
	 * */
	public abstract void completeTask(String businessId,
			Map<String, Object> variables);

	/**
	 * 查看流程图（流程还未启动）
	 * processDefinitionKey：流程定义的key值
	 * */
	public abstract InputStream showProcessImg(String processDefinitionKey);

	public abstract void showTaskProcessImg(String businessId);

}