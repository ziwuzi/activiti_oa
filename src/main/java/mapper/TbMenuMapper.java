package mapper;

import org.apache.ibatis.annotations.Param;
import po.TbMenu;
import po.query.RoleMenuQuery;

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

    List<RoleMenuQuery> getAllRoleMenu(@Param("roleId") Integer roleId,@Param("isEditRole") Boolean isEditRole);

    List<RoleMenuQuery> getTopRoleMenu(@Param("roleId") Integer roleId,@Param("isEditRole") Boolean isEditRole);

    List<RoleMenuQuery> getSubRoleMenu(@Param("roleId") Integer roleId,@Param("parentId") Integer parentId,@Param("isEditRole") Boolean isEditRole);

}