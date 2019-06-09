package mapper;

import org.apache.ibatis.annotations.Param;
import po.TbDaily;
import po.query.DailyData;

import java.util.List;

public interface TbDailyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbDaily record);

    int insertSelective(TbDaily record);

    TbDaily selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbDaily record);

    int updateByPrimaryKey(TbDaily record);

    List<TbDaily> getMyDailyList(int userId);

    Integer getMyDailyCount(int userId);

    List<TbDaily> getAllDailyList();

    Integer getAllDailyCount();

    List<DailyData> getDailyData(@Param("start") String start,@Param("end") String end);

    TbDaily getDaily(Integer dailyId);
}