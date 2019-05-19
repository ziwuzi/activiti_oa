package service;

import po.TbDaily;

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
}
