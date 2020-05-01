package acs.rest.users;


import acs.data.UserRole;
import acs.rest.utils.UserIdBoundary;
import acs.rest.utils.UserNameBoundray;

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
	private UserIdBoundary userId;
	private String avatar;
	private UserNameBoundray userName;
	private UserRole role;

	public UserBoundary() {

	}

	public UserBoundary(UserIdBoundary userId,  UserNameBoundray userName, UserRole role, String avatar) {
		super();
		setUserId(userId);
		setUserName(userName);
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

	@Override
	public String toString() {
		return "UserBoundry [userId=" + userId + ", avatar=" + avatar + ", userName=" + userName + ", role=" + role
				+ "]";
	}



	

}
