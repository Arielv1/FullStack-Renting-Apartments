package acs.rest.users;

import acs.data.UserRole;
import acs.rest.utils.UserNameBoundray;

/*
 * {
 * 
 * "userEmail": "tmoer32@gmail.com",
	"role" : "PLAYER",
	"userName": "tomer",
	"avatar": ";-)"
	
	}
 
 */

public class UserNewDetails {

	private String email;
	private UserNameBoundray userName;
	private UserRole role;
	private String avatar;
	
	
	public UserNewDetails(String email, UserNameBoundray userName, UserRole role, String avatar) {
		super();
		this.email = email;
		this.userName = userName;
		this.role = role;
		this.avatar = avatar;
	}
	
	


	public UserNewDetails() {
		super();
		
	}




	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public UserNameBoundray getUserName() {
		return userName;
	}


	public void setUserName(UserNameBoundray userName) {
		this.userName = userName;
	}


	public UserRole getRole() {
		return role;
	}


	public void setRole(UserRole role) {
		this.role = role;
	}


	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
}
