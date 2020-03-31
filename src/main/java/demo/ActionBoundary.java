package demo;

import java.util.Date;
import java.util.Map;

/*
 {
 	"actionId":{
 		"domain":"2020b.demo",
 		"id":"971"
 	},
 	"type":"actionType",
 	"element":{
 		"elementId":{
 			"domain":"2020b.demo",
 			"id":"54"
 	},
 	"createTypestamp":"2020-03-01T15:03:55.121+0000"
 	"invokedBy":{
 		"userId":{
 			"domain":"2020b.demo",
 			"email":"player@de.mo"
 		}
 	},
 	"actionAttributes":{
 	
 	}
 }
 */

public class ActionBoundary {

	private Map <String, Object> actionId;
	private String type;
	private Map <String, Object> element;
	private Date createdTimeStamp;
	private Map <String, Object> invokedBy;
	private Map <String, Object> actionAttributes;

	
	public ActionBoundary() {

	}

	public ActionBoundary(Map <String, Object> actionId, String type, Map <String, Object> element, Date createdTimeStamp,
			Map <String, Object> invokedBy,Map <String, Object> actionAttributes) {
		super();
		this.actionId = actionId;
		this.type = type;
		this.element = element;
		this.createdTimeStamp = createdTimeStamp;
		this.invokedBy = invokedBy;
		this.actionAttributes = actionAttributes;
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

	@Override
	public String toString() {
		return "ActionBoundary [actionId=" + actionId + ", type=" + type + ", element=" + element
				+ ", createdTimeStamp=" + createdTimeStamp + ", invokedBy=" + invokedBy + ", actionAttributes="
				+ actionAttributes + "]";
	}

}
