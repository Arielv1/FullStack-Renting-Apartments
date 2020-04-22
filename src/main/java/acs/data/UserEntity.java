package acs.data;

import java.util.Map;

public class UserEntity {
	private Map<String, Object> userId;
	private UserRole role; 
	private String username;
	private String avatar;
	
	
	public UserEntity() {
	}
	
	
	public UserEntity(Map<String, Object> userId, UserRole role, String username, String avatar) {
		super();
		this.userId = userId;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}


	public Map<String, Object> getUserId() {
		return userId;
	}
	public void setUserId(Map<String, Object> userId) {
		this.userId = userId;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


}
