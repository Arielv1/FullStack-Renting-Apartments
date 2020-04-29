package acs.rest.action;

import java.util.Date;
import java.util.Map;

import acs.rest.action.boundaries.InvokedByBoundary;
import acs.rest.action.boundaries.ElementBoundaryA;
import acs.rest.utils.IdBoundary;

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
 	"createdTimestamp":"2020-03-01T15:03:55.121+0000",
 	"invokedBy":{
 		"userId":{
 			"domain":"2020b.demo",
 			"email":"player@de.mo"
 		}
 	},
 	"actionAttributes":{
 		"key1":"can be set to any value you wish",
 		"key2":44.5,
 		"booleanValue":false,
 		"lastKey":"it can contain anything you wish"
 	}
 }
 */

public class ActionBoundary {
	
	private IdBoundary actionId;
	private String type;
	private ElementBoundaryA element;
	private Date createdTimestamp;
	private InvokedByBoundary invokedBy;
	private Map <String, Object> actionAttributes;

	
	public ActionBoundary() {

	}

	public ActionBoundary(IdBoundary actionId, String type, ElementBoundaryA element, Date createdTimestamp,
			InvokedByBoundary invokedBy,Map <String, Object> actionAttributes) {
		super();
		this.actionId = actionId;
		this.type = type;
		this.element = element;
		this.createdTimestamp = createdTimestamp;
		this.invokedBy = invokedBy;
		this.actionAttributes = actionAttributes;
	}

	public IdBoundary getActionId() {
		return actionId;
	}

	public void setActionId(IdBoundary actionId) {
		this.actionId = actionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ElementBoundaryA getElement() {
		return element;
	}

	public void setElement(ElementBoundaryA element) {
		this.element = element;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public InvokedByBoundary getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(InvokedByBoundary invokedBy) {
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
				+ ", createdTimestamp=" + createdTimestamp + ", invokedBy=" + invokedBy + ", actionAttributes="
				+ actionAttributes + "]";
	}

}
