package service.impl;

import mapper.TbMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import po.TbMenu;
import po.query.RoleMenuQuery;
import service.MenuService;

import java.util.ArrayList;
import java.util.List;
@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,timeout=5)
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    TbMenuMapper menuMapper;

    @Override
    public List<List<TbMenu>> getAllMenu() {
        List<List<TbMenu>> menuList = new ArrayList<>();
        List<TbMenu> topMenuList = menuMapper.getTopMenu();
        for(TbMenu menu : topMenuList){
            List<TbMenu> subMenuList = menuMapper.getSubMenu(menu.getId());
            subMenuList.add(0,menu);
            menuList.add(subMenuList);
        }
        return menuList;
    }

    @Override
    public List<List<RoleMenuQuery>> getMenu(Integer roleId, Boolean isEditRole) {
        List<List<RoleMenuQuery>> menuList = new ArrayList<>();
        List<RoleMenuQuery> topMenuList = menuMapper.getTopRoleMenu(roleId,isEditRole);
        for(RoleMenuQuery menu : topMenuList){
            List<RoleMenuQuery> subMenuList = menuMapper.getSubRoleMenu(roleId,menu.getId(),isEditRole);
            subMenuList.add(0,menu);
            menuList.add(subMenuList);
        }
        return menuList;
    }

    @Override
    public List<RoleMenuQuery> getRoleMenu(Integer roleId, Boolean isEditRole) {
        List<RoleMenuQuery> menuList = menuMapper.getAllRoleMenu(roleId,isEditRole);
        return menuList;
    }
}
