package po.query;

public class RoleMenuJson {
    private Integer id; //菜单id
    private Integer pId; //父id
    private String name; //菜单名

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPId() {
        return pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleMenuJson(){

    }

    public RoleMenuJson(RoleMenuQuery menuQuery){
        this.setId(menuQuery.getId());
        this.setPId(menuQuery.getParentId());
        this.setName(menuQuery.getName());
    }
}
