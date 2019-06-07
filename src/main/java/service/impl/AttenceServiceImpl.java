package service.impl;

import com.github.pagehelper.PageHelper;
import mapper.TbAttenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import po.LeaveApply;
import po.TbAttence;
import po.query.AttenceData;
import service.AttenceService;
import service.LeaveService;

import java.util.ArrayList;
import java.util.List;

@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,timeout=5)
@Service
public class AttenceServiceImpl implements AttenceService {
    @Autowired
    TbAttenceMapper attenceMapper;
    @Autowired
    LeaveService leaveService;

    private final static String DEFAULT_START = "09:00"; //默认上班时间
    private final static String DEFAULT_OFF = "18:00";   //默认下班时间

    @Override
    public List<TbAttence> getMyAttence(int userId, int firstRow, int rowCount) {
        PageHelper.startPage(firstRow,rowCount);
        return attenceMapper.getMyAttence(userId);
    }

    @Override
    public int getMyAttenceCount(int userId) {
        return attenceMapper.getMyAttenceCount(userId);
    }

    @Override
    public void addAttence(List<TbAttence> attenceList) {
        calculate(attenceList);
        for(TbAttence attence : attenceList){
            attenceMapper.delete(attence);
            attenceMapper.insert(attence);
        }
    }

    @Override
    public List<TbAttence> getAllAttence(int firstRow, int rowCount) {
        PageHelper.startPage(firstRow,rowCount,false);
        return attenceMapper.getAllAttence();
    }

    @Override
    public int getAllAttenceCount() {
        return attenceMapper.getAllAttenceCount();
    }

    @Override
    public List<AttenceData> getAttenceDatas(String status, String start, String end) {
        return attenceMapper.getAttenceDatas(status, start, end);
    }

    @Override
    public List<AttenceData> getAllAttenceDatas(String start, String end) {
        List<AttenceData> lateDate = attenceMapper.getAttenceDatas("迟到",start,end);
        List<AttenceData> earlyDate = attenceMapper.getAttenceDatas("早退",start,end);
        List<AttenceData> startErrDate = attenceMapper.getAttenceDatas("上班异常",start,end);
        List<AttenceData> offErrDate = attenceMapper.getAttenceDatas("下班异常",start,end);
        List<AttenceData> attenceDataList = new ArrayList<>();
        for (int i = 0; i < lateDate.size(); i++) {
            AttenceData attenceData = new AttenceData();
            attenceData.setUserName(lateDate.get(i).getUserName());
            attenceData.setLateCount(lateDate.get(i).getCount());
            attenceData.setEarlyCount(earlyDate.get(i).getCount());
            attenceData.setStartErrCount(startErrDate.get(i).getCount());
            attenceData.setOffErrCount(offErrDate.get(i).getCount());
            attenceDataList.add(attenceData);
        }
        return attenceDataList;
    }

    @Override
    public AttenceData getUserAttenceData(String start, String end, String name) {
        AttenceData lateDate = attenceMapper.getUserAttenceData("迟到",start,end,name);
        if(lateDate == null){
            return null;
        }
        AttenceData earlyDate = attenceMapper.getUserAttenceData("早退",start,end,name);
        AttenceData startErrDate = attenceMapper.getUserAttenceData("上班异常",start,end,name);
        AttenceData offErrDate = attenceMapper.getUserAttenceData("下班异常",start,end,name);
        AttenceData attenceData = new AttenceData();
        attenceData.setUserName(lateDate.getUserName());
        attenceData.setLateCount(lateDate.getCount());
        attenceData.setEarlyCount(earlyDate.getCount());
        attenceData.setStartErrCount(startErrDate.getCount());
        attenceData.setOffErrCount(offErrDate.getCount());
        return attenceData;
    }

    private void calculate(List<TbAttence> attenceList){
        for(TbAttence attence : attenceList){
            //计算工作日
            if(attence.getWeekday() != 0 && attence.getWeekday() != 6){
                attence.setWorkState(1);
            }
            else{
                attence.setWorkState(0);
            }
            //计算状态，迟到，早退，异常
            attence.setStatus("");
            if(attence.getWorkState() == 1){
                List<String> statusList = new ArrayList<>();
                if(StringUtils.isEmpty(attence.getStartTime())){
                    statusList.add("上班异常");
                }
                else if(attence.getStartTime().compareTo(DEFAULT_START) > 0) {
                    statusList.add("迟到");
                }
                if(StringUtils.isEmpty(attence.getOffTime())){
                    statusList.add("下班异常");
                }
                else if(attence.getOffTime().compareTo(DEFAULT_OFF) < 0) {
                    statusList.add("早退");
                }
                StringBuilder status = new StringBuilder();
                for(String s : statusList){
                    status.append(s).append(";");
                }
                attence.setStatus(status.toString());
            }
            //请假信息
            if(StringUtils.isEmpty(attence.getStatus())){
                attence.setLeaveInfo("");
            }
            else{
                LeaveApply leaveApply = leaveService.getLeaveByAttence(attence);
                if(leaveApply != null){
                    attence.setLeaveInfo(leaveApply.getLeave_type());
                }
            }
        }
    }
}
