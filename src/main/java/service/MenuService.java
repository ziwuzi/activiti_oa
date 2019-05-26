package service;

import po.TbMenu;
import po.User_role;
import po.query.RoleMenuQuery;

import java.util.List;

public interface MenuService {

    List<List<TbMenu>> getAllMenu();

    List<List<RoleMenuQuery>> getMenu(List<User_role> roleList, Boolean isEditRole);

    List<RoleMenuQuery> getRoleMenu(Integer roleId, Boolean isEditRole);

    void updateRoleMenu(Integer roleId, String menuString);
}
