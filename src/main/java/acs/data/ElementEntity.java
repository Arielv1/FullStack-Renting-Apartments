package acs.data;

import java.util.Date;
import java.util.Map;

public class ElementEntity {
	private String key;	// key = elementId = elementDomain + "!" + id
	private Map <String, Object> elementId;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private Map <String, Object> createdBy;
	private Map <String, Double> location; 
	private Map <String, Object> elementAttribues;
	
	public ElementEntity() {
	}

	public ElementEntity(String key, Map<String, Object> elementId, String type, String name, boolean active,
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public void setKey() {
		Object keyParts[] = this.getElementId().values().toArray();
		this.key = (String)keyParts[0] + "!" + (String)keyParts[1];
	}
	
	public void setKey(Map<String , Object> elementId) {
		
		Object keyParts[] = elementId.values().toArray();
		
		this.key = (String)keyParts[0]+ "!" +(String)keyParts[1];
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

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
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
		return "ElementEntity [key=" + key + ", elementId=" + elementId + ", type=" + type + ", name=" + name
				+ ", active=" + active + ", createdTimestamp=" + createdTimestamp + ", createdBy=" + createdBy
				+ ", location=" + location + ", elementAttribues=" + elementAttribues + "]";
	}
	
	
}
