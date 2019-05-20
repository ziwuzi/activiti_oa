package po;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-05-20
 */
public class TbRoleMenu {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 权限id
     */
    private Integer roleId;

    /**
     * 菜单id
     */
    private Integer menuId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}