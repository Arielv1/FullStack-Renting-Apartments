package acs.logic.user;


import org.springframework.stereotype.Service;

import acs.rest.users.UserBoundary;

@Service
public interface UserServiceInterface {
	public UserBoundary createUser(UserBoundary user);
	public UserBoundary login(String domain, String email);
	public UserBoundary updateUser(String domain, String email, UserBoundary update);
	public UserBoundary[] getAllUsers(String adminDomain, String adminEmail);
	public void deleteAllUsers(String adminDomain, String adminEmail);
	

}
