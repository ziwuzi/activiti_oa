package service;

import po.TbMenu;
import po.query.RoleMenuQuery;

import java.util.List;

public interface MenuService {

    List<List<TbMenu>> getAllMenu();

    List<List<RoleMenuQuery>> getMenu(Integer roleId, Boolean isEditRole);

    List<RoleMenuQuery> getRoleMenu(Integer roleId, Boolean isEditRole);

    void updateRoleMenu(Integer roleId, String menuString);
}
