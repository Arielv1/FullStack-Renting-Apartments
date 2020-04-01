package demo;

import java.util.*;
/*
    "elementId": {
        "ID": 1
    },
    "type": "demoType",
    "name": "demoName",
    "active": false,
    "createdTimeStamp": "2020-04-01T08:10:44.284+0000",
    "createdBy": {
    	"userid":{
    		"domain:"2020b.ofir.cohen",
        	"email": "ofir.cohen@gmail.com"
        	}
    },
    "location": {
        "lat": "00.00"
    },
    "elementAttribues": {
        "demoAttribute": "demoValue"
    }
 */
public class ElementBoundary {
	
	private Map <String, Object> elementId;
	private String type;
	private String name;
	private boolean active;	
	private Date createdTimeStamp;
	private Map <String, Object> createdBy;
	private Map <String, Object> location; 
	private Map <String, Object> elementAttribues;
	
	
	public ElementBoundary() {	
	}

	public ElementBoundary(Map<String, Object> elementId, String type, String name, boolean active,
			Date createdTimeStamp, Map<String, Object> createdBy, Map<String, Object> location,
			Map<String, Object> elementAttribues) {
		super();
		this.elementId = elementId;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimeStamp = createdTimeStamp;
		this.createdBy = createdBy;
		this.location = location;
		this.elementAttribues = elementAttribues;
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

	@Override
	public String toString() {
		return "ElementBoundary [elementId=" + elementId + ", type=" + type + ", name=" + name + ", active=" + active
				+ ", createdTimeStamp=" + createdTimeStamp + ", createdBy=" + createdBy + ", location=" + location
				+ ", elementAttribues=" + elementAttribues + "]";
	}
	
	
}
