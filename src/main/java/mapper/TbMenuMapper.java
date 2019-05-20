package mapper;

import po.TbMenu;

import java.util.List;

public interface TbMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbMenu record);

    int insertSelective(TbMenu record);

    TbMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbMenu record);

    int updateByPrimaryKey(TbMenu record);

    List<TbMenu> getTopMenu();

    List<TbMenu> getSubMenu(Integer parentId);
}