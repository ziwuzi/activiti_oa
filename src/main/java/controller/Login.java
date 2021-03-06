package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import po.TbMenu;
import po.User;
import po.User_role;
import po.query.RoleMenuQuery;
import service.LoginService;
import service.MenuService;

import java.util.ArrayList;
import java.util.List;


@Controller
public class Login {
	@Autowired
	LoginService loginservice;
	@Autowired
	MenuService menuService;
	
	@RequestMapping("/loginvalidate")
	public String loginValidate(@RequestParam("username") String username,@RequestParam("pic") String pic,@RequestParam("password") String pwd,
								HttpSession httpSession, HttpServletRequest request){
		String picode=(String) httpSession.getAttribute("rand");
		if(!picode.equalsIgnoreCase(pic))
			return "failcode";
		if(username==null)
			return "login";
		User user=loginservice.getUser(username);
		if(user.getPassword()!=null&&pwd.equals(user.getPassword()))
		{
			httpSession.setAttribute("user",user);
			httpSession.setAttribute("username", username);
			List<User_role> roleList = user.getUser_roles();
			List<List<RoleMenuQuery>> menuList = menuService.getMenu(roleList,false);
			httpSession.setAttribute("menuList",menuList);
			return "index";
		}else
			return "fail";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession httpSession){
		httpSession.removeAttribute("user");
		httpSession.removeAttribute("username");
		return "login";
	}
  }
