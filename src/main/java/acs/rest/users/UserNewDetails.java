package acs.rest.users;



import acs.data.UserRole;


/*
 * {
 * 
 * "email": "tmoer32@gmail.com",
 *  "username": "tomer arnon", 
	"role" : "PLAYER",
	"avatar": ";-)"
	
	}
 
 */

public class UserNewDetails {

	private String email;
	private String username;
	private UserRole role;
	private String avatar;

	public UserNewDetails(String email, String username, UserRole role, String avatar) {
		super();
		setEmail(email);
		setUsername(username);
		setRole(role);
		setAvatar(avatar);
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		if (avatar != null && !avatar.trim().isEmpty()) {
			this.avatar = avatar;
		} else {
			throw new RuntimeException("avatar invalid!!");
		}

	}

}
