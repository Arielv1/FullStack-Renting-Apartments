package acs.rest.element;

import java.util.*;
/*
 * 	"key" : "2020b.ofir.cohen!123456" <------ might be temp , will possibly removed
    "elementId": {
    	"domain" : "2020B.Ofir.Cohen"
        "ID": 1
    },
    "type": "demoType",
    "name": "demoName",
    "active": false,
    "createdTimestamp": "2020-04-01T08:10:44.284+0000",
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
	private String key;
	private Map <String, Object> elementId;
	private String type;
	private String name;
	private Boolean active;	
	private Date createdTimestamp;
	private Map <String, Object> createdBy;
	private Map <String, Double> location; 
	private Map <String, Object> elementAttribues;
	
	
	public ElementBoundary() {	
	}

	public ElementBoundary(String key, Map<String, Object> elementId, String type, String name, Boolean active,
			Date createdTimestamp, Map<String, Object> createdBy, Map<String, Double> location,
			Map<String, Object> elementAttribues) {
		super();
		this.key = key;
		this.elementId = elementId;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.location = location;
		this.elementAttribues = elementAttribues;
	}
	
	public ElementBoundary(Map<String, Object> elementId, String type, String name, Boolean active,
			Date createdTimestamp, Map<String, Object> createdBy, Map<String, Double> location,
			Map<String, Object> elementAttribues) {
		super();	
		this.elementId = elementId;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.location = location;
		this.elementAttribues = elementAttribues;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Map<String, Object> getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Map<String, Object> createdBy) {
		this.createdBy = createdBy;
	}

	public Map<String, Double> getLocation() {
		return location;
	}

	public void setLocation(Map<String, Double> location) {
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
				+ ", createdTimestamp=" + createdTimestamp + ", createdBy=" + createdBy + ", location=" + location
				+ ", elementAttribues=" + elementAttribues + "]";
	}

	

	


	
}
