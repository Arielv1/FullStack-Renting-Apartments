package acs.data;

import java.util.Map;

public class UserEntity {
	private Map<String, Object> userId;
	private String role; // check if it is need to change
	private String username;
	private String avatar;
	
	
	public UserEntity() {
	}
	
	public Map<String, Object> getUserId() {
		return userId;
	}
	public void setUserId(Map<String, Object> userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


}
