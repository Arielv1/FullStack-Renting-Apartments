package acs.rest.admin;

import java.util.Arrays;

import acs.rest.users.UserBoundary;

/*
{

	"userID":{
		"domain:"2020b.demo",
		"email":"demo@us.er"
		},
	"role":"ADMIN"
	"username":"Demo User",
	"avatar":";-)"
 }
 */
public class AdminBoundary {
	private UserBoundary admin;

	

	public AdminBoundary(UserBoundary admin) {
		super();
		this.admin = admin;

	}
	public AdminBoundary() {
	}



	public UserBoundary getAdmin() {
		return admin;
	}



	public void setAdmin(UserBoundary admin) {
		this.admin = admin;
	}	
}
