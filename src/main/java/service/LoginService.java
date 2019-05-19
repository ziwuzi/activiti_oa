package service;


import po.User;

public interface LoginService {
	String getPwdByName(String name);

	User getUser(String name);
}
