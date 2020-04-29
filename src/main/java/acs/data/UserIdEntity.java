package acs.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserIdEntity {
	
	@Column(name = "USER_DOMAIN", insertable = false, updatable = false)
	private String domain;
	private String email;
	
	public UserIdEntity() {
	}
	
	public UserIdEntity(String domain, String email) {
		super();
		this.domain = domain;
		this.email = email;
	}
	
	@Column(name = "USER_DOMAIN")
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserIdEntity [domain=" + domain + ", email=" + email + "]";
	}
	
	
}
