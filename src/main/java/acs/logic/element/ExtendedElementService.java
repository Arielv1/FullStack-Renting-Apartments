package acs.logic.element;

import java.util.Set;

import acs.rest.element.boundaries.ElementBoundary;
import acs.rest.utils.IdBoundary;

public interface ExtendedElementService extends ElementService{
	
	public void bindChildToParent(String elementDomain, String elementId, IdBoundary childId);
	public Set<ElementBoundary> getChildren(String elementDomain, String elementId);
	public ElementBoundary getParent (String elementDomain, String elementId);
}
