package service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;

import po.LeaveApply;

public interface LeaveService {
	public ProcessInstance startWorkflow(LeaveApply apply,String userid,Map<String,Object> variables);
	public List<LeaveApply> getPageDeptTask(String userid, int firstrow, int rowcount);
	public int getAllDeptTask(String userid);
	public LeaveApply getLeave(int id);
	public List<LeaveApply> getPageHrTask(String userid, int firstrow, int rowcount);
	public int getAllHrTask(String userid);
	public List<LeaveApply> getPageXJTask(String userid, int firstrow, int rowcount);
	public int getAllXJTask(String userid);
	public List<LeaveApply> getPageUpdateApplyTask(String userid, int firstrow, int rowcount);
	public int getAllUpdateApplyTask(String userid);
	public void completeReportBack(String taskid, String realstart_time, String realend_time);
	public void updateComplete(String taskid, LeaveApply leave, String reappply);
	public List<String> getHighLightedFlows(ProcessDefinitionEntity deployedProcessDefinition,List<HistoricActivityInstance> historicActivityInstances);
}
