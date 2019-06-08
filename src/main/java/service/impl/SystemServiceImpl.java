package service.impl;

import java.util.ArrayList;
import java.util.List;

import mapper.PermissionMapper;
import mapper.RoleMapper;
import mapper.UserMapper;
import mapper.UserRoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import po.Permission;
import po.Role;
import po.Role_permission;
import po.User;
import po.UserRole;
import po.User_role;
import service.SystemService;

import com.github.pagehelper.PageHelper;
@Service
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
public class SystemServiceImpl implements SystemService{
	@Autowired
	UserMapper usermapper;
	
	@Autowired
	RoleMapper rolemapper;
	
	@Autowired
	PermissionMapper permissionmapper;
	
	@Autowired
	UserRoleMapper userRoleMapper;
	
	public List<User> getAllUsers() {
		return usermapper.getusers();
	}
	public List<User> getPageUsers(int pagenum, int pagesize) {
		PageHelper.startPage(pagenum,pagesize);  
		List<User> l=usermapper.getusers();
		return l;
	}
	public User getUserById(int id) {
		User u=usermapper.getUserByid(id);
		return u;
	}
	public List<Role> getRoles() {
		return rolemapper.getRoles();
	}
	public void deleteUser(int uid) {
		usermapper.deleteuser(uid);
	}
	public void addUser(User user, String[] rolenames) {
		usermapper.adduser(user);
		for(String rolename:rolenames){
			Role role=rolemapper.getRoleidbyName(rolename);
			User_role ur=new User_role();
			ur.setRole(role);
			ur.setUser(user);
			rolemapper.adduserrole(ur);
		}
	}
	public void addUser(User user) {
		usermapper.adduser(user);
	}
	public void updateUser(int uid, User user, String[] rolenames) {
		if(rolenames==null){
			user.setUid(uid);
			usermapper.updateuser(user);
			usermapper.deleteuserrole(uid);
		}
		else{
			user.setUid(uid);
			usermapper.updateuser(user);
			usermapper.deleteuserrole(uid);
			for(String rolename:rolenames){
				Role role=rolemapper.getRoleidbyName(rolename);
				User_role ur=new User_role();
				ur.setRole(role);
				ur.setUser(user);
				rolemapper.adduserrole(ur);
			}
		}
		
	}
	public List<Role> getPageRoleInfo(int pagenum, int pagesize) {
		PageHelper.startPage(pagenum,pagesize);
		List<Role> l=rolemapper.getRoleinfo();
		List<Role> result = new ArrayList<>();
		int start = pagenum * pagesize - pagesize;
		int end = pagenum * pagesize;
		if(start > l.size()){
			start = l.size();
		}
		if(end > l.size()){
			end = l.size();
		}
		for(int i = start ; i < end ; i++){
			result.add(l.get(i));
		}
		return result;
	}
	public List<Role> getRoleInfo() {
		return rolemapper.getRoleinfo();
	}
	public List<Permission> getPermisions() {
		return permissionmapper.getPermissions();
	}
	public void addRole(Role role, String[] permissionnames) {
		rolemapper.addRole(role);
		for(String permissionname:permissionnames){
			Permission p=permissionmapper.getPermissionByname(permissionname);
			Role_permission rp=new Role_permission();
			rp.setRole(role);
			rp.setPermission(p);
			rolemapper.addRolePermission(rp);
		}
	}
	public void deleteRole(int rid) {
		rolemapper.deleterole(rid);
		rolemapper.deleterole_permission(rid);
		rolemapper.deleteuser_role(rid);
	}
	public Role getRoleById(int rid) {
		return rolemapper.getRolebyid(rid);
	}
	public void deleteRolePermission(int rid) {
		rolemapper.deleterole_permission(rid);
	}
	public void updateRole(int rid, String[] permissionnames) {
		Role role=rolemapper.getRolebyid(rid);
		for(String permissionname:permissionnames){
			Permission p=permissionmapper.getPermissionByname(permissionname);
			Role_permission rp=new Role_permission();
			rp.setRole(role);
			rp.setPermission(p);
			rolemapper.addRolePermission(rp);
		}
	}
	public List<Permission> getPagePermisions(int pagenum, int pagesize) {
		PageHelper.startPage(pagenum,pagesize);  
		return permissionmapper.getPermissions();
	}
	public void addPermission(String permissionname) {
		permissionmapper.addpermission(permissionname);
	}
	public void deletePermission(int pid) {
		permissionmapper.deletepermission(pid);
		permissionmapper.deleteRole_permission(pid);
	}
	public int getUidByUsername(String username) {
		return usermapper.getUidByusername(username);
	}
	
	@Override
	public List<UserRole> listRolesByUserId(int userid) {
		return userRoleMapper.listUserRoleByUid(userid);
	}
	
	

}
