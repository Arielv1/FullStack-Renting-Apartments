package acs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Embeddable
public class ElementIdEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String domain;
	private String id;
	
	public ElementIdEntity() {
	}
	
	public ElementIdEntity(String domain, String id) {
		super();
		this.domain = domain;
		this.id = id;
	}
	
	@Column(name = "ELEMENT_DOMAIN")
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	@Column(name = "ELEMENT_ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ElementIdEntity [domain=" + domain + ", id=" + id + "]";
	}
	
}
