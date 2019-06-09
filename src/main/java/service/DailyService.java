package service;

import pagemodel.DataGrid;
import po.TbDaily;
import po.query.DailyData;

import java.util.List;

public interface DailyService {

    List<TbDaily> getDailyList(Integer userId);

    Integer getDailyCount(Integer userId);

    List<TbDaily> getDailyList();

    Integer getDailyCount();

    TbDaily getDaily(Integer dailyId);

    void addDaily(TbDaily daily);

    void deleteDaily(Integer dailyId);

    List<TbDaily> getDailyList(int userId, int pageNum, int pageSize);

    List<TbDaily> getDailyList(int pageNum, int pageSize);

    public DataGrid<DailyData> getDailyData(String start, String end, DataGrid<DailyData> dataGrid);

    void update(TbDaily daily);
}
