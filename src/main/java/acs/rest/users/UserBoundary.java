package acs.rest.users;


import acs.data.UserRole;
import acs.rest.utils.UserIdBoundary;
import acs.rest.utils.UserNameBoundray;

/*
 {
 	"userId": { 
 		"domain" : "2020.b@demo",
 		"email" : "tomerarnon@gmail.com"
 	},
 	"role" : "PLAYER",
 	"userName" : "demo user",
 	"avatar" : ";-)"
 } 
 * 
 */

public class UserBoundary {
	private UserIdBoundary userId;
	private String avatar;
	private UserNameBoundray username;
	private UserRole role;

	public UserBoundary() {

	}

	public UserBoundary(UserIdBoundary userId,  UserNameBoundray username, UserRole role, String avatar) {
		super();
		setUserId(userId);
		setUserName(username);
		setRole(role);
		setAvatar(avatar);
	}

	public UserIdBoundary getUserId() {
		return userId;
	}

	public void setUserId(UserIdBoundary userId) {
		this.userId = userId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public UserNameBoundray getUsername() {
		return username;
	}

	public void setUserName(UserNameBoundray username) {
		this.username = username;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserBoundry [userId=" + userId + ", avatar=" + avatar + ", userName=" + username + ", role=" + role
				+ "]";
	}



	

}
