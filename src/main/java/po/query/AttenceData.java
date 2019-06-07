package po.query;

public class AttenceData {
    private String userName;
    private Integer count;
    private Integer lateCount;
    private Integer earlyCount;
    private Integer startErrCount;
    private Integer offErrCount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getLateCount() {
        return lateCount;
    }

    public void setLateCount(Integer lateCount) {
        this.lateCount = lateCount;
    }

    public Integer getEarlyCount() {
        return earlyCount;
    }

    public void setEarlyCount(Integer earlyCount) {
        this.earlyCount = earlyCount;
    }

    public Integer getStartErrCount() {
        return startErrCount;
    }

    public void setStartErrCount(Integer startErrCount) {
        this.startErrCount = startErrCount;
    }

    public Integer getOffErrCount() {
        return offErrCount;
    }

    public void setOffErrCount(Integer offErrCount) {
        this.offErrCount = offErrCount;
    }
}
