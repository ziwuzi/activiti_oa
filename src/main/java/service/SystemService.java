package service;

import java.util.List;

import po.Permission;
import po.Role;
import po.User;
import po.UserRole;

public interface SystemService {
	List<User> getAllUsers();
	List<User> getPageUsers(int pagenum, int pagesize);
	User getUserById(int id);
	List<Role> getRoles();
	void deleteUser(int uid);
	void addUser(User user, String[] rolenames);
	void addUser(User user);//只添加用户，无角色添加
	void updateUser(int uid, User user, String[] rolenames);
	List<Role> getPageRoleInfo(int pagenum, int pagesize);
	List<Role> getRoleInfo();
	List<Permission> getPermisions();
	/**
	 * 使用用户id获取角色id列表
	 * @param userid
	 * @return
	 */
	List<UserRole> listRolesByUserId(int userid);
	void addRole(Role role, String[] permissionnames);
	void deleteRole(int rid);
	Role getRoleById(int rid);
	void deleteRolePermission(int rid);//删除rid的角色下的所有权利
	void updateRole(int rid, String[] permissionnames);//把所有的权利permissionnames添加到rid的角色下
	List<Permission> getPagePermisions(int pagenum, int pagesize);
	void addPermission(String permissionname);
	void deletePermission(int pid);
	int getUidByUsername(String username);
}
