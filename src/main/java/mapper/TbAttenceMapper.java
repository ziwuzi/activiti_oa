package mapper;

import po.TbAttence;

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
}