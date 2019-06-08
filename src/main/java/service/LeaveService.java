package service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;

import po.LeaveApply;
import po.TbAttence;
import po.query.LeaveData;

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
	List<LeaveApply> getPageTask(int firstrow, int rowcount);
	Integer getPageTaskCount();
	List<LeaveApply> getAllPageTask(int firstrow, int rowcount);
	Integer getAllPageTaskCount();

    LeaveApply getLeaveByTaskId(String taskId);

	List<LeaveApply> getMyLeaveTask(String userName,int pageNum,int pageSize);

	int getMyLeaveCount(String userName);

    List<LeaveApply> getAllLeaveTask(int firstRow, int rowCount);

	int getAllLeaveCount();

	void update(LeaveApply leaveApply);

    LeaveApply getLeaveByAttence(TbAttence attence);

    List<LeaveData> getAllLeaveData(String start, String end);

    String[] getUserLeaveData(String start, String end, String name);

    boolean checkExist(LeaveApply apply);
}
