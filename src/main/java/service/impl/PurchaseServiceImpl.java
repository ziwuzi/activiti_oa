package service.impl;

import java.util.Map;

import mapper.PurchaseApplyMapper;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import po.PurchaseApply;
import service.PurchaseService;
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service
public class PurchaseServiceImpl implements PurchaseService{
	@Autowired
	PurchaseApplyMapper purchasemapper;
	@Autowired
	IdentityService identityservice;
	@Autowired
	RuntimeService runtimeservice;
	@Autowired
	TaskService taskservice;
	
	public ProcessInstance startWorkflow(PurchaseApply apply, String userid,Map<String, Object> variables) {
		purchasemapper.save(apply);
		String businesskey=String.valueOf(apply.getId());//使用leaveapply表的主键作为businesskey,连接业务数据和流程数据
		identityservice.setAuthenticatedUserId(userid);
		ProcessInstance instance=runtimeservice.startProcessInstanceByKey("purchase",businesskey,variables);
		String instanceid=instance.getId();
		apply.setProcess_instance_id(instanceid);
		purchasemapper.update(apply);
		return instance;
	}

	public PurchaseApply getPurchase(int id) {
		PurchaseApply apply = purchasemapper.get(id);
		Task task = taskservice.createTaskQuery().executionId(apply.getProcess_instance_id()).singleResult();
		apply.setTask(task);
		return apply;
	}

	public void updatePurchase(PurchaseApply a) {
		purchasemapper.update(a);
	}

}
