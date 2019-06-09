package controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.pagehelper.StringUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pagemodel.DataGrid;
import pagemodel.HistoryProcess;
import pagemodel.LeaveTask;
import pagemodel.Process;
import pagemodel.RunningProcess;
import po.LeaveApply;
import po.Permission;
import po.Role;
import po.Role_permission;
import po.User;
import po.User_role;
import po.query.LeaveData;
import service.LeaveService;
import service.SystemService;

import com.alibaba.fastjson.JSON;
import util.ActivitiUtil;

@Controller
public class ActivitiController {
	@Autowired
	RepositoryService rep;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	FormService formService;
	@Autowired
	IdentityService identityService;
	@Autowired
	LeaveService leaveService;
	@Autowired
	TaskService taskService;
	@Autowired
	HistoryService historyService;
	@Autowired
	SystemService systemService;

	@RequestMapping("/processlist")
	String process(){
		return "activiti/processlist";
	}

	@RequestMapping("/uploadworkflow")
	public String fileUpload(@RequestParam MultipartFile uploadfile, HttpServletRequest request){
		try{
			MultipartFile file=uploadfile;
			String filename=file.getOriginalFilename();
			InputStream is=file.getInputStream();
			rep.createDeployment().addInputStream(filename, is).deploy();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "index";
	}

	@RequestMapping(value="/getprocesslists")
	@ResponseBody
	public DataGrid<Process> getList(@RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		int firstrow=(current-1)*rowCount;
		List<ProcessDefinition> list=rep.createProcessDefinitionQuery().listPage(firstrow, rowCount);
		int total=rep.createProcessDefinitionQuery().list().size();
		List<Process> mylist=new ArrayList<Process>();
		for(int i=0;i<list.size();i++)
		{
			Process p=new Process();
			p.setDeploymentId(list.get(i).getDeploymentId());
			p.setId(list.get(i).getId());
			p.setKey(list.get(i).getKey());
			p.setName(list.get(i).getName());
			p.setResourceName(list.get(i).getResourceName());
			p.setDiagramresourcename(list.get(i).getDiagramResourceName());
			mylist.add(p);
		}
		DataGrid<Process> grid=new DataGrid<Process>();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setRows(mylist);
		grid.setTotal(total);
		return grid;
	}


	@RequestMapping("/showresource")
	public void export(@RequestParam("pdid") String pdid,@RequestParam("resource") String resource,HttpServletResponse response) throws Exception{
		ProcessDefinition def=rep.createProcessDefinitionQuery().processDefinitionId(pdid).singleResult();
		InputStream is=rep.getResourceAsStream(def.getDeploymentId(), resource);
		ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(is, output);
	}

	@RequestMapping("/deletedeploy")
	public String deleteDeploy(@RequestParam("deployid") String deployid) throws Exception{
		rep.deleteDeployment(deployid,true);
		return "activiti/processlist";
	}

	@RequestMapping("/runningprocess")
	public String task(){
		return "activiti/runningprocess";
	}

	@RequestMapping("/deptleaderaudit")
	public String myTask(){
		return "activiti/deptleaderaudit";
	}

	@RequestMapping("/hraudit")
	public String hr(){
		return "activiti/hraudit";
	}

	@RequestMapping("/index")
	public String my(){
		return "index";
	}

	@RequestMapping("/leaveapply")
	public String leave(){
		return "activiti/leaveapply";
	}

	@RequestMapping("/reportback")
	public String reprotBack(){
		return "activiti/reportback";
	}

	@RequestMapping("/modifyapply")
	public String modifyApply(){
		return "activiti/modifyapply";
	}

	@RequestMapping(value="/startleave",method=RequestMethod.POST)
	@ResponseBody
	public String startLeave(LeaveApply apply, HttpSession session){
		String userid=(String) session.getAttribute("username");
		apply.setUser_id(userid);
		if(leaveService.checkExist(apply)){
			return JSON.toJSONString("exist");
		}
		Map<String,Object> variables=new HashMap<String, Object>();
		variables.put("applyuserid", userid);
		ProcessInstance ins= leaveService.startWorkflow(apply, userid, variables);
		System.out.println("流程id"+ins.getId()+"已启动");
		return JSON.toJSONString("success");
	}

	@RequestMapping(value="/depttasklist",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public DataGrid<LeaveTask> getDeptTaskList(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		DataGrid<LeaveTask> grid=new DataGrid<LeaveTask>();
		grid.setRowCount(rowCount);
		grid.setCurrent(current);
		grid.setTotal(0);
		grid.setRows(new ArrayList<LeaveTask>());
		//先做权限检查，对于没有部门领导审批权限的用户,直接返回空
		String userid=(String) session.getAttribute("username");
		int uid= systemService.getUidByUsername(userid);
		User user= systemService.getUserById(uid);
		List<User_role> userroles=user.getUser_roles();
		if(userroles==null)
			return grid;
		boolean flag=false;//默认没有权限
		for(int k=0;k<userroles.size();k++){
			User_role ur=userroles.get(k);
			Role r=ur.getRole();
			int roleid=r.getRid();
			Role role= systemService.getRoleById(roleid);
			List<Role_permission> p=role.getRole_permission();
			for(int j=0;j<p.size();j++){
				Role_permission rp=p.get(j);
				Permission permission=rp.getPermission();
				if(permission.getPermissionname().equals("部门领导审批"))
					flag=true;
				else
					continue;
			}
		}
			if(flag==false)//无权限
			{
				return grid;
			}else{
				int firstrow=(current-1)*rowCount;
				List<LeaveApply> results= leaveService.getPageDeptTask(userid,firstrow,rowCount);
				int totalsize= leaveService.getAllDeptTask(userid);
				List<LeaveTask> tasks = getLeaveTasks(results);
				grid.setRowCount(rowCount);
				grid.setCurrent(current);
				grid.setTotal(totalsize);
				grid.setRows(tasks);
				return grid;
			}
	}

	@RequestMapping(value="/hrtasklist",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public DataGrid<LeaveTask> getHrTaskList(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		DataGrid<LeaveTask> grid=new DataGrid<LeaveTask>();
		grid.setRowCount(rowCount);
		grid.setCurrent(current);
		grid.setTotal(0);
		grid.setRows(new ArrayList<LeaveTask>());
		//先做权限检查，对于没有人事权限的用户,直接返回空
		String userid=(String) session.getAttribute("username");
		int uid= systemService.getUidByUsername(userid);
		User user= systemService.getUserById(uid);
		List<User_role> userroles=user.getUser_roles();
		if(userroles==null)
			return grid;
		boolean flag=false;//默认没有权限
		for(int k=0;k<userroles.size();k++){
			User_role ur=userroles.get(k);
			Role r=ur.getRole();
			int roleid=r.getRid();
			Role role= systemService.getRoleById(roleid);
			List<Role_permission> p=role.getRole_permission();
			for(int j=0;j<p.size();j++){
				Role_permission rp=p.get(j);
				Permission permission=rp.getPermission();
				if(permission.getPermissionname().equals("人事审批"))
					flag=true;
				else
					continue;
			}
		}
			if(flag==false)//无权限
			{
				return grid;
			}else{
		int firstrow=(current-1)*rowCount;
		List<LeaveApply> results= leaveService.getPageHrTask(userid,firstrow,rowCount);
		int totalsize= leaveService.getAllHrTask(userid);
				List<LeaveTask> tasks = getLeaveTasks(results);
				grid.setRowCount(rowCount);
		grid.setCurrent(current);
		grid.setTotal(totalsize);
		grid.setRows(tasks);
		return grid;
			}
	}

	@RequestMapping(value="/xjtasklist",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getXJTaskList(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		int firstrow=(current-1)*rowCount;
		String userid=(String) session.getAttribute("username");
		List<LeaveApply> results= leaveService.getPageXJTask(userid,firstrow,rowCount);
		int totalsize= leaveService.getAllXJTask(userid);
		List<LeaveTask> tasks = getLeaveTasks(results);
		DataGrid<LeaveTask> grid=new DataGrid<LeaveTask>();
		grid.setRowCount(rowCount);
		grid.setCurrent(current);
		grid.setTotal(totalsize);
		grid.setRows(tasks);
		return JSON.toJSONString(grid);
	}


	@RequestMapping(value="/updatetasklist",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getUpdateTaskList(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		int firstrow=(current-1)*rowCount;
		String userid=(String) session.getAttribute("username");
		List<LeaveApply> results= leaveService.getPageUpdateApplyTask(userid,firstrow,rowCount);
		int totalsize= leaveService.getAllUpdateApplyTask(userid);
		List<LeaveTask> tasks = getLeaveTasks(results);
		DataGrid<LeaveTask> grid=new DataGrid<LeaveTask>();
		grid.setRowCount(rowCount);
		grid.setCurrent(current);
		grid.setTotal(totalsize);
		grid.setRows(tasks);
		return JSON.toJSONString(grid);
	}

	private List<LeaveTask> getLeaveTasks(List<LeaveApply> results) {
		List<LeaveTask> tasks = new ArrayList<LeaveTask>();
		for (LeaveApply apply : results) {
			LeaveTask task = new LeaveTask();
			task.setApply_time(apply.getApply_time());
			task.setUser_id(apply.getUser_id());
			task.setEnd_time(apply.getEnd_time());
			task.setId(apply.getId());
			task.setLeave_type(apply.getLeave_type());
			task.setProcess_instance_id(apply.getProcess_instance_id());
			task.setReason(apply.getReason());
			task.setStart_time(apply.getStart_time());
			task.setState(apply.getState());
			if(apply.getTask() != null) {
				task.setProcessdefid(apply.getTask().getProcessDefinitionId());
				task.setTaskcreatetime(apply.getTask().getCreateTime());
				task.setTaskid(apply.getTask().getId());
				task.setTaskname(apply.getTask().getName());
			}
			tasks.add(task);
		}
		return tasks;
	}

	@RequestMapping(value="/dealtask")
	@ResponseBody
	public String taskDeal(@RequestParam("taskid") String taskid, HttpServletResponse response){
		Task task= taskService.createTaskQuery().taskId(taskid).singleResult();
		ProcessInstance process= runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		LeaveApply leave= leaveService.getLeave(new Integer(process.getBusinessKey()));
		return JSON.toJSONString(leave);
	}

	@RequestMapping(value="/activiti/task-deptleaderaudit")
	String url(){
		return "/activiti/task-deptleaderaudit";
	}

	@RequestMapping(value="/task/deptcomplete/{taskid}")
	@ResponseBody
	public String deptComplete(HttpSession session, @PathVariable("taskid") String taskid, HttpServletRequest req){
		String userid=(String) session.getAttribute("username");
		Map<String,Object> variables=new HashMap<String,Object>();
		String approve=req.getParameter("deptleaderapprove");
		if(!"true".equals(approve)) {
			LeaveApply leaveApply = leaveService.getLeaveByTaskId(taskid);
			leaveApply.setState(2);
			leaveService.update(leaveApply);
		}
		variables.put("deptleaderapprove", approve);
		taskService.claim(taskid, userid);
		taskService.complete(taskid, variables);
		return JSON.toJSONString("success");
	}

	@RequestMapping(value="/task/hrcomplete/{taskid}")
	@ResponseBody
	public String hrComplete(HttpSession session, @PathVariable("taskid") String taskid, HttpServletRequest req){
		String userid=(String) session.getAttribute("username");
		Map<String,Object> variables=new HashMap<String,Object>();
		String approve=req.getParameter("hrapprove");
		if(!"true".equals(approve)) {
			LeaveApply leaveApply = leaveService.getLeaveByTaskId(taskid);
			leaveApply.setState(2);
			leaveService.update(leaveApply);
		}
		variables.put("hrapprove", approve);
		taskService.claim(taskid, userid);
		taskService.complete(taskid, variables);
		return JSON.toJSONString("success");
	}

	@RequestMapping(value="/task/reportcomplete/{taskid}")
	@ResponseBody
	public String reportBackComplete(@PathVariable("taskid") String taskid, HttpServletRequest req){
		String realstart_time=req.getParameter("realstart_time");
		String realend_time=req.getParameter("realend_time");
		leaveService.completeReportBack(taskid,realstart_time,realend_time);
		return JSON.toJSONString("success");
	}

	@RequestMapping(value="/task/updatecomplete/{taskid}")
	@ResponseBody
	public String updateComplete(@PathVariable("taskid") String taskid, @ModelAttribute("leave") LeaveApply leave, @RequestParam("reapply") String reapply){
		leaveService.updateComplete(taskid,leave,reapply);
		return JSON.toJSONString("success");
	}

	@RequestMapping("involvedprocess")//参与的正在运行的请假流程
	@ResponseBody
	public DataGrid<RunningProcess> allExeution(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		int firstrow=(current-1)*rowCount;
		String userid=(String) session.getAttribute("username");
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
		int total= (int) query.count();
		List<ProcessInstance> a = query.processDefinitionKey("leave").involvedUser(userid).listPage(firstrow, rowCount);
		List<RunningProcess> list=new ArrayList<RunningProcess>();
		for(ProcessInstance p:a){
			RunningProcess process=new RunningProcess();
			process.setActivityid(p.getActivityId());
			process.setBusinesskey(p.getBusinessKey());
			process.setExecutionid(p.getId());
			process.setProcessInstanceid(p.getProcessInstanceId());
			list.add(process);
		}
		DataGrid<RunningProcess> grid=new DataGrid<RunningProcess>();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setTotal(total);
		grid.setRows(list);
		return grid;
	}

	@RequestMapping("/getfinishprocess")
	@ResponseBody
	public DataGrid<HistoryProcess> getHistory(HttpSession session,@RequestParam("current") int current,@RequestParam("rowCount") int rowCount){
		String userid=(String) session.getAttribute("username");
		HistoricProcessInstanceQuery process = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("leave").startedBy(userid).finished();
		int total= (int) process.count();
		int firstrow=(current-1)*rowCount;
		List<HistoricProcessInstance> info = process.listPage(firstrow, rowCount);
		List<HistoryProcess> list=new ArrayList<HistoryProcess>();
		for(HistoricProcessInstance history:info){
			HistoryProcess his=new HistoryProcess();
			String bussinesskey=history.getBusinessKey();
			LeaveApply apply= leaveService.getLeave(Integer.parseInt(bussinesskey));
			his.setLeaveapply(apply);
			his.setBusinessKey(bussinesskey);
			his.setProcessDefinitionId(history.getProcessDefinitionId());
			list.add(his);
		}
		DataGrid<HistoryProcess> grid=new DataGrid<HistoryProcess>();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setTotal(total);
		grid.setRows(list);
		return grid;
	}


	@RequestMapping("/historyprocess")
	public String history(){
		return "activiti/historyprocess";
	}


	@RequestMapping("/processinfo")
	@ResponseBody
	public List<HistoricActivityInstance> processInfo(@RequestParam("instanceid")String instanceid){
		  List<HistoricActivityInstance> his = historyService.createHistoricActivityInstanceQuery().processInstanceId(instanceid).orderByHistoricActivityInstanceStartTime().asc().list();
		  return his;
	}

	@RequestMapping("/processhis")
	@ResponseBody
	public List<HistoricActivityInstance> processHis(@RequestParam("ywh")String ywh){
		  String instanceid= historyService.createHistoricProcessInstanceQuery().processDefinitionKey("purchase").processInstanceBusinessKey(ywh).singleResult().getId();
		  System.out.println(instanceid);
		  List<HistoricActivityInstance> his = historyService.createHistoricActivityInstanceQuery().processInstanceId(instanceid).orderByHistoricActivityInstanceStartTime().asc().list();
		  return his;
	}

	@RequestMapping("myleaveprocess")
	String myLeaveProcess(){
		return "activiti/myleaveprocess";
	}

	@RequestMapping("traceprocess/{executionid}")
	public void traceProcess(@PathVariable("executionid")String executionid, HttpServletResponse response) throws Exception{
		ProcessInstance process= runtimeService.createProcessInstanceQuery().processInstanceId(executionid).singleResult();
		BpmnModel bpmnmodel=rep.getBpmnModel(process.getProcessDefinitionId());
		List<String> activeActivityIds= runtimeService.getActiveActivityIds(executionid);
		DefaultProcessDiagramGenerator gen=new DefaultProcessDiagramGenerator();
		 // 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）
	    List<HistoricActivityInstance> historicActivityInstances = historyService
	            .createHistoricActivityInstanceQuery().executionId(executionid)
	            .orderByHistoricActivityInstanceStartTime().asc().list();
	    // 计算活动线
	    List<String> highLightedFlows = leaveService.getHighLightedFlows(
	                    (ProcessDefinitionEntity) ((RepositoryServiceImpl) rep)
	                            .getDeployedProcessDefinition(process.getProcessDefinitionId()),
	                    historicActivityInstances);

		InputStream in=gen.generateDiagram(bpmnmodel, "png", activeActivityIds,highLightedFlows,"宋体","宋体",null,1.0);
	    //InputStream in=gen.generateDiagram(bpmnmodel, "png", activeActivityIds);
	    ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(in, output);
	}

	@RequestMapping("myleaves")
	String myLeaves(){
		return "activiti/myleaves";
	}

	@RequestMapping("setupprocess")
	@ResponseBody
	public DataGrid<RunningProcess> setupProcess(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		int firstrow=(current-1)*rowCount;
		String userid=(String) session.getAttribute("username");
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
		int total= (int) query.count();
		List<ProcessInstance> a = query.processDefinitionKey("leave").involvedUser(userid).listPage(firstrow, rowCount);
		List<RunningProcess> list=new ArrayList<RunningProcess>();
		for(ProcessInstance p:a){
			RunningProcess process=new RunningProcess();
			process.setActivityid(p.getActivityId());
			process.setBusinesskey(p.getBusinessKey());
			process.setExecutionid(p.getId());
			process.setProcessInstanceid(p.getProcessInstanceId());
			LeaveApply l= leaveService.getLeave(Integer.parseInt(p.getBusinessKey()));
			if(l.getUser_id().equals(userid))
			list.add(process);
			else
			continue;
		}
		DataGrid<RunningProcess> grid=new DataGrid<RunningProcess>();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setTotal(total);
		grid.setRows(list);
		return grid;
	}


	@RequestMapping("leave/to_my_leave")
	String toMyLeave(){
		return "leave/my_leave";
	}

	@RequestMapping(value="leave/my_leave",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public DataGrid<LeaveTask> getMyLeave(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount) {
		DataGrid<LeaveTask> grid = new DataGrid<>(current, rowCount);
		String userName=(String) session.getAttribute("username");
		List<LeaveApply> results = leaveService.getMyLeaveTask(userName,current, rowCount);
		int totalSize = leaveService.getMyLeaveCount(userName);
		List<LeaveTask> tasks = getLeaveTasks(results);
		grid = new DataGrid<>(current, rowCount, totalSize, tasks);
		return grid;
	}

	@RequestMapping("leave/to_my_audit")
	String toMyAudit(){
		return "leave/my_audit";
	}

	@RequestMapping(value="leave/my_audit",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public DataGrid<LeaveTask> getMyAudit(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		DataGrid<LeaveTask> grid=new DataGrid<>(current,rowCount);
		//先做权限检查，对于没有部门领导审批权限的用户,直接返回空
		String userid=(String) session.getAttribute("username");
		int uid= systemService.getUidByUsername(userid);
		User user= systemService.getUserById(uid);
		List<User_role> userroles=user.getUser_roles();
		if(userroles==null)
			return grid;
		boolean flag=false;//默认没有权限
		for(int k=0;k<userroles.size();k++){
			User_role ur=userroles.get(k);
			Role r=ur.getRole();
			int roleid=r.getRid();
			Role role= systemService.getRoleById(roleid);
			List<Role_permission> p=role.getRole_permission();
			for(int j=0;j<p.size();j++){
				Role_permission rp=p.get(j);
				Permission permission=rp.getPermission();
				if(permission.getPermissionname().equals("部门领导审批") || permission.getPermissionname().equals("人事审批") )
					flag=true;
				else
					continue;
			}
		}
		if(flag==false)//无权限
		{
			return grid;
		}else{
			int firstRow=(current-1)*rowCount;
			List<LeaveApply> results= leaveService.getPageTask(firstRow,rowCount);
			int totalSize= leaveService.getPageTaskCount();
			List<LeaveTask> tasks = getLeaveTasks(results);
			grid = new DataGrid<>(current,rowCount,totalSize,tasks);
			return grid;
		}
	}

	@RequestMapping("leave/to_all_leave")
	String toAllAudit(){
		return "leave/all_leave";
	}

	@RequestMapping(value="leave/all_leave",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public DataGrid<LeaveTask> getAllLeave(HttpSession session, @RequestParam("current") int current, @RequestParam("rowCount") int rowCount) {
        DataGrid<LeaveTask> grid = new DataGrid<>(current, rowCount);
        List<LeaveApply> results = leaveService.getAllLeaveTask(current, rowCount);
        int totalSize = leaveService.getAllLeaveCount();
        List<LeaveTask> tasks = getLeaveTasks(results);
        grid = new DataGrid<>(current, rowCount, totalSize, tasks);
        return grid;
	}

    @RequestMapping(value="leave/cancel_leave/{id}",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object cancelLeave(@PathVariable("id") String id) throws Exception {
		if(!StringUtil.isEmpty(id)) {
			LeaveApply leaveApply = leaveService.getLeave(Integer.valueOf(id));
			leaveApply.setState(3);
			leaveService.update(leaveApply);
			Task task= ActivitiUtil.getTaskByProcessId(leaveApply.getProcess_instance_id());
			ActivitiUtil.endProcess(task.getId());
		}
		return JSON.toJSONString("success");
    }

	@RequestMapping("leave/to_leave_chart")
	String toLeaveChart(){
		return "leave/leave_chart";
	}

	@RequestMapping(value="leave/leave_chart",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public Object getLeaveChart(@RequestParam("start") String start,@RequestParam("end") String end){
		if(StringUtils.isEmpty(start)) {
			start="0000-00-00";
		}
		if(StringUtils.isEmpty(end)) {
			end="9999-99-99";
		}
		List<LeaveData> dataList = leaveService.getAllLeaveData(start,end);
		return JSON.toJSON(dataList);
	}

	@RequestMapping(value="leave/leave_chart_name",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public Object getLeaveChartName(@RequestParam("start") String start,@RequestParam("end") String end,@RequestParam("name") String name){
		if(StringUtils.isEmpty(start)) {
			start="0000-00-00";
		}
		if(StringUtils.isEmpty(end)) {
			end="9999-99-99";
		}
		String [] dataList = leaveService.getUserLeaveData(start,end,name);
		if(dataList == null){
			return JSON.toJSONString("noName");
		}
		return JSON.toJSON(dataList);
	}

}
