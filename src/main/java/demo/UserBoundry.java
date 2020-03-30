package demo;

import java.util.Map;

/*
 {
 	"userId": { 
 		"domain" : "2020.b@demo",
 		"email" : "tomerarnon@gmail.com"
 	},
 	"role" : "client",
 	"userName" : "demo user",
 	"avatar" : ";-)"
 } 
 
 * 
 */


public class UserBoundry {
	private Map<String, Object> userId;
	private String avatar;
	private String userName;
	private String role;

	public UserBoundry() {

	}

	public UserBoundry(Map<String, Object> userId,  String userName, String role, String avatar) {
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserBoundry [userId=" + userId + ", avatar=" + avatar + ", userName=" + userName + ", role=" + role
				+ "]";
	}



	

}
