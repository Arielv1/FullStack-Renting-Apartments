 package acs.data;

import java.util.Date;
import java.util.Map;

public class ActionEntity {
	private Map <String, Object> actionId;
	private String type;
	private Map <String, Object> element;
	private Date createdTimeStamp;
	private Map <String, Object> invokedBy;
	private Map <String, Object> actionAttributes;
	
	
	public ActionEntity() {
	}
	
	public Map<String, Object> getActionId() {
		return actionId;
	}
	public void setActionId(Map<String, Object> actionId) {
		this.actionId = actionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Object> getElement() {
		return element;
	}
	public void setElement(Map<String, Object> element) {
		this.element = element;
	}
	public Date getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	public void setCreatedTimeStamp(Date createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}
	public Map<String, Object> getInvokedBy() {
		return invokedBy;
	}
	public void setInvokedBy(Map<String, Object> invokedBy) {
		this.invokedBy = invokedBy;
	}
	public Map<String, Object> getActionAttributes() {
		return actionAttributes;
	}
	public void setActionAttributes(Map<String, Object> actionAttributes) {
		this.actionAttributes = actionAttributes;
	}

}
