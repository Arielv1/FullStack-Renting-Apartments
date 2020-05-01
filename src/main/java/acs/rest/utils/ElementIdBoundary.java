package acs.rest.utils;

public class ElementIdBoundary {
	
	private String domain;
	private String id;
	
	public ElementIdBoundary() {
	}
	
	public ElementIdBoundary(String domain, String id) {
		super();
		this.domain = domain;
		this.id = id;
	}
	
	
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
		return "ElementId [domain=" + domain + ", id=" + id + "]";
	}
	
	
	
}
