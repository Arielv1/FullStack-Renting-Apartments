package acs.data.actions;

import acs.rest.utils.IdBoundary;

public class ActionElementEntity {
	private IdBoundary elementId;

	public ActionElementEntity() {
	}
	
	public ActionElementEntity(IdBoundary elementId) {
		super();
		this.elementId = elementId;
	}
	
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
