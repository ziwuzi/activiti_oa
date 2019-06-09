package service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.TbDailyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pagemodel.DataGrid;
import po.TbDaily;
import po.query.DailyData;
import service.DailyService;

import java.util.List;

@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,timeout=5)
@Service
public class DailyServiceImpl implements DailyService {
    @Autowired
    TbDailyMapper dailyMapper;

    @Override
    public List<TbDaily> getDailyList(Integer userId) {
        return null;
    }

    @Override
    public Integer getDailyCount(Integer userId) {
        return dailyMapper.getMyDailyCount(userId);
    }

    @Override
    public List<TbDaily> getDailyList() {
        return null;
    }

    @Override
    public Integer getDailyCount() {
        return dailyMapper.getAllDailyCount();
    }

    @Override
    public TbDaily getDaily(Integer dailyId) {
        return dailyMapper.getDaily(dailyId);
    }

    @Override
    public void addDaily(TbDaily daily) {
        dailyMapper.insert(daily);
    }

    @Override
    public void deleteDaily(Integer dailyId) {

    }

    @Override
    public List<TbDaily> getDailyList(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return dailyMapper.getMyDailyList(userId);
    }

    @Override
    public List<TbDaily> getDailyList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return dailyMapper.getAllDailyList();
    }

    @Override
    public DataGrid<DailyData> getDailyData(String start, String end, DataGrid<DailyData> dataGrid) {
        PageHelper.startPage(dataGrid.getCurrent(),dataGrid.getRowCount());
        List<DailyData> dailyDataList = dailyMapper.getDailyData(start,end);
        PageInfo<DailyData> pageInfo = new PageInfo<>(dailyDataList);
        dataGrid.setRows(dailyDataList);
        dataGrid.setTotal((int) pageInfo.getTotal());
        return dataGrid;
    }

    @Override
    public void update(TbDaily daily) {
        dailyMapper.updateByPrimaryKey(daily);
    }
}
