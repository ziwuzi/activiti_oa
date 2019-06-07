package po;

/**
 * 
 * 
 * @author zhengwzh
 * 
 * @date 2019-06-04
 */
public class TbAttence {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 日期
     */
    private String date;

    /**
     * 上班时间
     */
    private String startTime;

    /**
     * 下班时间
     */
    private String offTime;

    /**
     * 考勤状态
     */
    private String status;

    /**
     * 请假信息
     */
    private String leaveInfo;

    /**
     * 是否工作日
     */
    private Integer workState;

    /**
     * 备注
     */
    private String remark;

    /**
     * 星期几
     */
    private Integer weekday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime == null ? null : offTime.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeaveInfo() {
        return leaveInfo;
    }

    public void setLeaveInfo(String leaveInfo) {
        this.leaveInfo = leaveInfo == null ? null : leaveInfo.trim();
    }

    public Integer getWorkState() {
        return workState;
    }

    public void setWorkState(Integer workState) {
        this.workState = workState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }
}