package acs.data.actions;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import acs.rest.utils.IdBoundary;

@Embeddable
public class ActionElementEntity  {
	private IdBoundary elementId;

	public ActionElementEntity() {
	}
	
	public ActionElementEntity(IdBoundary elementId) {
		super();
		this.elementId = elementId;
	}
	
	@Embedded
	public IdBoundary getElementId() {
		return elementId;
	}

	public void setElementId(IdBoundary elementId) {
		this.elementId = elementId;
	}

	@Override
	public String toString() {
		return "elementEntiny [elementId=" + elementId + "]";
	}
}
