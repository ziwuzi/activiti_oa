package mapper;

import org.apache.ibatis.annotations.Param;
import po.LeaveApply;
import po.TbAttence;

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
}
