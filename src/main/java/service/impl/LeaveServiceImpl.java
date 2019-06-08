package service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.github.pagehelper.PageHelper;
import mapper.LeaveApplyMapper;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import po.LeaveApply;
import po.TbAttence;
import po.query.LeaveData;
import po.query.LeaveDataDetail;
import service.LeaveService;
import util.DateTool;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service
public class LeaveServiceImpl implements LeaveService{
	@Autowired
	LeaveApplyMapper leaveApplyMapper;
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	ManagementService managementService;
	
	public ProcessInstance startWorkflow(LeaveApply apply, String userid, Map<String, Object> variables) {
		apply.setApply_time(new Date().toString());
		apply.setUser_id(userid);
		apply.setState(0);
		leaveApplyMapper.save(apply);
		String businesskey=String.valueOf(apply.getId());//使用leaveapply表的主键作为businesskey,连接业务数据和流程数据
		identityService.setAuthenticatedUserId(userid);
		ProcessInstance instance= runtimeService.startProcessInstanceByKey("leave",businesskey,variables);
		System.out.println(businesskey);
		String instanceid=instance.getId();
		apply.setProcess_instance_id(instanceid);
		leaveApplyMapper.update(apply);
		return instance;
	}

	public List<LeaveApply> getPageDeptTask(String userid, int firstrow, int rowcount) {
		List<LeaveApply> results=new ArrayList<LeaveApply>();
		List<Task> tasks= taskService.createTaskQuery().taskCandidateGroup("部门经理").listPage(firstrow, rowcount);
		getTask(results, tasks);
		return results;
	}

	private void getTask(List<LeaveApply> results, List<Task> tasks) {
		for (Task task : tasks) {
			String instanceid = task.getProcessInstanceId();
			ProcessInstance ins = runtimeService.createProcessInstanceQuery().processInstanceId(instanceid).singleResult();
			String businesskey = ins.getBusinessKey();
			LeaveApply a = leaveApplyMapper.get(Integer.parseInt(businesskey));
			a.setTask(task);
			results.add(a);
		}
	}

	public int getAllDeptTask(String userid) {
		List<Task> tasks= taskService.createTaskQuery().taskCandidateGroup("部门经理").list();
		return tasks.size();
	}

	public LeaveApply getLeave(int id) {
		LeaveApply leave= leaveApplyMapper.get(id);
		return leave;
	}

	public List<LeaveApply> getPageHrTask(String userid, int firstrow, int rowcount) {
		List<LeaveApply> results=new ArrayList<LeaveApply>();
		List<Task> tasks= taskService.createTaskQuery().taskCandidateGroup("人事").listPage(firstrow, rowcount);
		getTask(results, tasks);
		return results;
	}

	public int getAllHrTask(String userid) {
		List<Task> tasks= taskService.createTaskQuery().taskCandidateGroup("人事").list();
		return tasks.size();
	}
	
	public List<LeaveApply> getPageXJTask(String userid, int firstrow, int rowcount) {
		List<LeaveApply> results=new ArrayList<LeaveApply>();
		List<Task> tasks= taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskName("销假").listPage(firstrow, rowcount);
		getTask(results, tasks);
		return results;
	}

	public int getAllXJTask(String userid) {
		List<Task> tasks= taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskName("销假").list();
		return tasks.size();
	}
	
	public List<LeaveApply> getPageUpdateApplyTask(String userid, int firstrow, int rowcount) {
		List<LeaveApply> results=new ArrayList<LeaveApply>();
		List<Task> tasks= taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskName("调整申请").listPage(firstrow, rowcount);
		getTask(results, tasks);
		return results;
	}
	
	public int getAllUpdateApplyTask(String userid) {
		List<Task> tasks= taskService.createTaskQuery().taskCandidateOrAssigned(userid).taskName("调整申请").list();
		return tasks.size();
	}
	
	public void completeReportBack(String taskid, String realstart_time, String realend_time) {
		Task task= taskService.createTaskQuery().taskId(taskid).singleResult();
		String instanceid=task.getProcessInstanceId();
		ProcessInstance ins= runtimeService.createProcessInstanceQuery().processInstanceId(instanceid).singleResult();
		String businesskey=ins.getBusinessKey();
		LeaveApply a= leaveApplyMapper.get(Integer.parseInt(businesskey));
		a.setReality_start_time(realstart_time);
		a.setReality_end_time(realend_time);
		a.setState(1);
		leaveApplyMapper.update(a);
		taskService.complete(taskid);
	}

	public void updateComplete(String taskid, LeaveApply leave, String reapply) {
		Task task= taskService.createTaskQuery().taskId(taskid).singleResult();
		String instanceid=task.getProcessInstanceId();
		ProcessInstance ins= runtimeService.createProcessInstanceQuery().processInstanceId(instanceid).singleResult();
		String businesskey=ins.getBusinessKey();
		LeaveApply a= leaveApplyMapper.get(Integer.parseInt(businesskey));
		a.setLeave_type(leave.getLeave_type());
		a.setStart_time(leave.getStart_time());
		a.setEnd_time(leave.getEnd_time());
		a.setReason(leave.getReason());
		Map<String,Object> variables=new HashMap<String,Object>();
		variables.put("reapply", reapply);
		if(reapply.equals("true")){
			leaveApplyMapper.update(a);
			taskService.complete(taskid,variables);
		}else
			taskService.complete(taskid,variables);
	}
	
	public List<String> getHighLightedFlows(  
	        ProcessDefinitionEntity processDefinitionEntity,  
	        List<HistoricActivityInstance> historicActivityInstances) {  
	  
	    List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId  
	    for (int i = 0; i < historicActivityInstances.size(); i++) {// 对历史流程节点进行遍历  
	        ActivityImpl activityImpl = processDefinitionEntity  
	                .findActivity(historicActivityInstances.get(i)  
	                        .getActivityId());// 得 到节点定义的详细信息  
	        List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点  
	        if ((i + 1) >= historicActivityInstances.size()) {  
	            break;  
	        }  
	        ActivityImpl sameActivityImpl1 = processDefinitionEntity  
	                .findActivity(historicActivityInstances.get(i + 1)  
	                        .getActivityId());// 将后面第一个节点放在时间相同节点的集合里  
	        sameStartTimeNodes.add(sameActivityImpl1);  
	        for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {  
	            HistoricActivityInstance activityImpl1 = historicActivityInstances  
	                    .get(j);// 后续第一个节点  
	            HistoricActivityInstance activityImpl2 = historicActivityInstances  
	                    .get(j + 1);// 后续第二个节点  
	            if (activityImpl1.getStartTime().equals(  
	                    activityImpl2.getStartTime())) {// 如果第一个节点和第二个节点开始时间相同保存  
	                ActivityImpl sameActivityImpl2 = processDefinitionEntity  
	                        .findActivity(activityImpl2.getActivityId());  
	                sameStartTimeNodes.add(sameActivityImpl2);  
	            } else {// 有不相同跳出循环  
	                break;  
	            }  
	        }  
	        List<PvmTransition> pvmTransitions = activityImpl  
	                .getOutgoingTransitions();// 取出节点的所有出去的线  
	        for (PvmTransition pvmTransition : pvmTransitions) {// 对所有的线进行遍历  
	            ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition  
	                    .getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示  
	            if (sameStartTimeNodes.contains(pvmActivityImpl)) {  
	                highFlows.add(pvmTransition.getId());  
	            }  
	        }  
	    }  
	    return highFlows;  
	}

	public List<LeaveApply> getPageTask(int firstrow, int rowcount) {
		List<LeaveApply> results=new ArrayList<LeaveApply>();
		List<Task> tasks= taskService.createNativeTaskQuery()
				.sql("SELECT * FROM " + managementService.getTableName(Task.class) + " T WHERE T.NAME_ = #{taskName1} OR T.NAME_ = #{taskName2}")
				.parameter("taskName1", "部门领导审批")
				.parameter("taskName2", "人事审批")
				.listPage(firstrow, rowcount);
		getTask(results, tasks);
		return results;
	}

	public Integer getPageTaskCount() {
		List<Task> tasks= taskService.createNativeTaskQuery()
				.sql("SELECT * FROM " + managementService.getTableName(Task.class) + " T WHERE T.NAME_ = #{taskName1} OR T.NAME_ = #{taskName2}")
				.parameter("taskName1", "部门领导审批")
				.parameter("taskName2", "人事审批")
				.list();
		return tasks.size();
	}

	public List<LeaveApply> getAllPageTask(int firstrow, int rowcount) {
		List<LeaveApply> results=new ArrayList<LeaveApply>();
		List<Task> tasks= taskService.createNativeTaskQuery()
				.sql("SELECT * FROM " + managementService.getTableName(Task.class) + " T WHERE T.NAME_ = #{taskName1} OR T.NAME_ = #{taskName2}")
				.listPage(firstrow, rowcount);
		getTask(results, tasks);
		return results;
	}

	public Integer getAllPageTaskCount() {
		List<Task> tasks= taskService.createNativeTaskQuery()
				.sql("SELECT * FROM " + managementService.getTableName(Task.class) + " T WHERE T.NAME_ = #{taskName1} OR T.NAME_ = #{taskName2}")
				.list();
		return tasks.size();
	}

	@Override
	public LeaveApply getLeaveByTaskId(String taskId) {
		return leaveApplyMapper.getByTaskId(taskId);
	}

	public List<LeaveApply> getMyLeaveTask(String userName,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<LeaveApply> applies = leaveApplyMapper.getByUser(userName);
		for(LeaveApply apply : applies){
			Task task = taskService.createTaskQuery().executionId(apply.getProcess_instance_id()).singleResult();
			apply.setTask(task);
		}
		return applies;
	}

	@Override
	public int getMyLeaveCount(String userName) {
		return leaveApplyMapper.getMyLeaveCount(userName);
	}

	@Override
	public List<LeaveApply> getAllLeaveTask(int firstRow, int rowCount) {
		PageHelper.startPage(firstRow,rowCount);
		List<LeaveApply> applies = leaveApplyMapper.getAll();
		for(LeaveApply apply : applies){
			Task task = taskService.createTaskQuery().executionId(apply.getProcess_instance_id()).singleResult();
			apply.setTask(task);
		}
		return applies;
	}

	@Override
	public int getAllLeaveCount() {
		return leaveApplyMapper.getAllCount();
	}

	@Override
	public void update(LeaveApply leaveApply) {
		leaveApplyMapper.update(leaveApply);
	}

	@Override
	public LeaveApply getLeaveByAttence(TbAttence attence) {
		String date = "0000-00-00";
		try {
			date = DateTool.strintToDateString2(attence.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return leaveApplyMapper.getByAttence(attence.getUserId(),date);
	}

	@Override
	public List<LeaveData> getAllLeaveData(String start, String end) {
		String [] leaveTypes = {"事假","病假","年假","丧假","产假"};
		List<LeaveData> leaveDataList = new ArrayList<>();
		for(String leaveType : leaveTypes){
			LeaveData leaveData = new LeaveData();
			List<LeaveDataDetail> dataDetails = leaveApplyMapper.getLeaveData(start,end,leaveType);
			leaveData.setDataDetails(dataDetails);
			leaveData.setLeaveType(leaveType);
			leaveDataList.add(leaveData);
		}
		return leaveDataList;
	}

	@Override
	public String[] getUserLeaveData(String start, String end, String name) {
		String [] leaveTypes = {"事假","病假","年假","丧假","产假"};
		String [] result = new String[leaveTypes.length];
		for (int i = 0; i < leaveTypes.length; i++) {
			LeaveDataDetail dataDetail = leaveApplyMapper.getUserLeaveData(start,end,leaveTypes[i],name);
			if(dataDetail == null){
				return null;
			}
			result[i] = String.valueOf(dataDetail.getCount());
		}
		return result;
	}

	public static void main(String args[]){
		Boolean test = Pattern.matches(". 1$","3 1");
	}
}
