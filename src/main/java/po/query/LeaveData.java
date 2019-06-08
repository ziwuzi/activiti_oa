package po.query;

import java.util.List;

public class LeaveData {
    private String leaveType;
    private List<LeaveDataDetail> dataDetails;

    public List<LeaveDataDetail> getDataDetails() {
        return dataDetails;
    }

    public void setDataDetails(List<LeaveDataDetail> dataDetails) {
        this.dataDetails = dataDetails;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

}
