package acs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ActionIdEntity {
	
private static final long serialVersionUID = 1L;
	
	private String domain;
	private String id;
	
	public ActionIdEntity() {
	}
	
	public ActionIdEntity(String domain, String id) {
		super();
		this.domain = domain;
		this.id = id;
	}
	
	@Column(name = "ACTION_DOMAIN")
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ActionIdEntity [domain=" + domain + ", id=" + id + "]";
	}

	
}
