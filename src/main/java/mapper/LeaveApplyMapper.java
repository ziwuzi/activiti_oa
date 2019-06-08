package mapper;

import org.apache.ibatis.annotations.Param;
import po.LeaveApply;
import po.query.LeaveDataDetail;

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

    LeaveApply getByAttence(@Param("userId")Integer userId,@Param("date")String date);

    List<LeaveDataDetail> getLeaveData(@Param("start") String start, @Param("end") String end, @Param("leaveType") String leaveType);

	LeaveDataDetail getUserLeaveData(@Param("start") String start, @Param("end") String end, @Param("leaveType") String leaveType,@Param("name") String name);

	int checkExist(LeaveApply apply);
}
