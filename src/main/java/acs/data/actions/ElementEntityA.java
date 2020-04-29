package acs.data.actions;

import acs.rest.utils.IdBoundary;

public class ElementEntityA {
	private IdBoundary elementId;

	public ElementEntityA() {
	}
	
	public ElementEntityA(IdBoundary elementId) {
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
