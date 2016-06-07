package com.mswang.learn.flow.taskListener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 员工经理任务分配
 *
 */
@Component("managerTaskHandler")
@Scope(value = "prototype")
public class ManagerTaskHandler implements TaskListener {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3624948887111822686L;

	//指定个人任务的办理人
	@Override
	public void notify(DelegateTask delegateTask) {
		delegateTask.setAssignee("");
	}

}
