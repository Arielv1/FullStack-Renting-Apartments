package acs.data.actions;

import java.util.Date;
import java.util.Map;

import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import acs.dal.MapToJsonConverter;
import acs.data.utils.ActionIdEntity;
import acs.rest.action.boundaries.ActionElementBoundary;
import acs.rest.action.boundaries.InvokedByBoundary;
import acs.rest.utils.IdBoundary;

@Entity
@Table(name = "ACTIONS")
public class ActionEntity {
	private ActionIdEntity actionId;
	private String type;
	private ActionElementEntity element;
	private Date createdTimestamp;
	private InvokedByEntity invokedBy;
	private Map<String, Object> actionAttributes;

	public ActionEntity() {
	}

	@Id
	@Embedded
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

	@Embedded
	public ActionElementEntity getElement() {
		return element;
	}

	public void setElement(ActionElementEntity element) {
		this.element = element;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@Embedded
	public InvokedByEntity getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(InvokedByEntity invokedBy) {
		this.invokedBy = invokedBy;
	}

	@Convert(converter = MapToJsonConverter.class)
	@Lob
	public Map<String, Object> getActionAttributes() {
		return actionAttributes;
	}

	public void setActionAttributes(Map<String, Object> actionAttributes) {
		this.actionAttributes = actionAttributes;
	}

}
