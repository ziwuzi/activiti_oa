package mapper;

import po.TbRoleMenu;

public interface TbRoleMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbRoleMenu record);

    int insertSelective(TbRoleMenu record);

    TbRoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbRoleMenu record);

    int updateByPrimaryKey(TbRoleMenu record);
}