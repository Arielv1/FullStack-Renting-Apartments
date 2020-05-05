package acs.data;


import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

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

@javax.persistence.Entity
@Table(name = "USERS")
public class UserEntity {
	private UserIdEntity userId;
	private String avatar;
	private UserNameEntity userName;
	private UserRole role;

	public UserEntity() {

	}

	public UserEntity(UserIdEntity userId,  UserNameEntity userName, UserRole role, String avatar) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.role = role;
		this.avatar = avatar;
	}

	@Id
	@Embedded
	public UserIdEntity getUserId() {
		return userId;
	}

	public void setUserId(UserIdEntity userId) {
		this.userId = userId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Embedded
	public UserNameEntity getUserName() {
		return userName;
	}

	public void setUserName(UserNameEntity userName) {
		this.userName = userName;
	}

	@Enumerated(EnumType.STRING)
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
