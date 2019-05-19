package mapper;

import po.TbDaily;

import java.util.List;

public interface TbDailyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbDaily record);

    int insertSelective(TbDaily record);

    TbDaily selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbDaily record);

    int updateByPrimaryKey(TbDaily record);

    List<TbDaily> getDailyList(int userId);

    Integer getDailyCount(int userId);
}