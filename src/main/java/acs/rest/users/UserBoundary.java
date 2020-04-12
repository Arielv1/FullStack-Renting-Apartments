package acs.rest.users;

import java.util.Map;

import acs.data.UserRole;

/*
 {
 	"userId": { 
 		"domain" : "2020.b@demo",
 		"email" : "tomerarnon@gma"il.com"
 	},
 	"role" : "PLAYER",
 	"userName" : "demo user",
 	"avatar" : ";-)"
 } 
 * 
 */

public class UserBoundary {
	private Map<String, Object> userId;
	private String avatar;
	private String userName;
	private UserRole role;

	public UserBoundary() {

	}

	public UserBoundary(Map<String, Object> userId,  String userName, UserRole role, String avatar) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.role = role;
		this.avatar = avatar;
	}

	public Map<String, Object> getUserId() {
		return userId;
	}

	public void setUserId(Map<String, Object> userId) {
		this.userId = userId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserBoundry [userId=" + userId + ", avatar=" + avatar + ", userName=" + userName + ", role=" + role
				+ "]";
	}



	

}
