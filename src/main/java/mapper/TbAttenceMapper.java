package mapper;

import org.apache.ibatis.annotations.Param;
import po.TbAttence;
import po.query.AttenceData;

import java.util.List;

public interface TbAttenceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAttence record);

    int insertSelective(TbAttence record);

    TbAttence selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAttence record);

    int updateByPrimaryKey(TbAttence record);

    List<TbAttence> getMyAttence(int userId);

    int getMyAttenceCount(int userId);

    void delete(TbAttence attence);

    List<TbAttence> getAllAttence();

    int getAllAttenceCount();

    List<AttenceData> getAttenceDatas(@Param("status") String status,@Param("start") String start,@Param("end") String end);

    AttenceData getUserAttenceData(@Param("status") String status,@Param("start") String start,@Param("end") String end,@Param("name") String name);
}