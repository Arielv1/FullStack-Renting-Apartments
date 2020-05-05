package acs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/*
 "userid":{
    		"domain:"2020b.ofir.cohen",
        	"email": "ofir.cohen@gmail.com"
        	}
 */
@Embeddable
public class UserIdEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	@Column(name = "USER_EMAIL")
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
