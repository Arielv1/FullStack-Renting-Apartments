 package acs.data;

import java.util.Date;
import java.util.Map;

import acs.data.actions.ActionElementEntity;
import acs.data.actions.InvokedByEntity;
import acs.rest.action.boundaries.ActionElementBoundary;
import acs.rest.action.boundaries.InvokedByBoundary;
import acs.rest.utils.IdBoundary;

public class ActionEntity {
	private ActionIdEntity actionId;
	private String type;
	private ActionElementEntity element;
	private Date createdTimestamp;
	private InvokedByEntity invokedBy;
	private Map <String, Object> actionAttributes;
	

	public ActionEntity() {
	}
	
	public ActionIdEntity getActionId() {
		return actionId;
	}
	public void setActionId(ActionIdEntity actionId) {
		this.actionId = actionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ActionElementEntity getElement() {
		return element;
	}
	public void setElement(ActionElementEntity element) {
		this.element = element;
	}
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public InvokedByEntity getInvokedBy() {
		return invokedBy;
	}
	public void setInvokedBy(InvokedByEntity invokedBy) {
		this.invokedBy = invokedBy;
	}
	public Map<String, Object> getActionAttributes() {
		return actionAttributes;
	}
	public void setActionAttributes(Map<String, Object> actionAttributes) {
		this.actionAttributes = actionAttributes;
	}

}
