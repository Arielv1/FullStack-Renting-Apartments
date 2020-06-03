package acs.rest.admin;

import acs.rest.users.UserBoundary;


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
