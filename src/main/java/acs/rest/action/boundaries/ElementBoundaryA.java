package acs.rest.action.boundaries;

import acs.rest.utils.IdBoundary;

public class ElementBoundaryA {
	private IdBoundary elementId;
	
	public ElementBoundaryA() {
	}
	
	public ElementBoundaryA(IdBoundary elementId) {
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
		return "elementBoundary [elementId=" + elementId + "]";
	}


}
