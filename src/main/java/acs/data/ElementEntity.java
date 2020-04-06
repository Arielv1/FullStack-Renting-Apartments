package acs.data;

import java.util.Date;
import java.util.Map;

public class ElementEntity {
	private Map <String, Object> elementId;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimeStamp;
	private Map <String, Object> createdBy;
	private Map <String, Object> location; 
	private Map <String, Object> elementAttribues;
	
	public ElementEntity() {
	}
	
	public Map<String, Object> getElementId() {
		return elementId;
	}
	public void setElementId(Map<String, Object> elementId) {
		this.elementId = elementId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	public void setCreatedTimeStamp(Date createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}
	public Map<String, Object> getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Map<String, Object> createdBy) {
		this.createdBy = createdBy;
	}
	public Map<String, Object> getLocation() {
		return location;
	}
	public void setLocation(Map<String, Object> location) {
		this.location = location;
	}
	public Map<String, Object> getElementAttribues() {
		return elementAttribues;
	}
	public void setElementAttribues(Map<String, Object> elementAttribues) {
		this.elementAttribues = elementAttribues;
	}

}
