package mapper;

import po.LeaveApply;

import java.util.List;

public interface LeaveApplyMapper {
	void save(LeaveApply apply);
	LeaveApply get(int id);
	void update(LeaveApply app);
	LeaveApply getByTaskId(String taskId);

	List<LeaveApply> getByUser(String userName);

	int getMyLeaveCount(String userName);

	List<LeaveApply> getAll();

	int getAllCount();
}
