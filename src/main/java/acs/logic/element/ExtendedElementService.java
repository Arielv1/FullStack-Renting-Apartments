package acs.logic.element;

import java.util.Set;

import acs.rest.element.boundaries.ElementBoundary;
import acs.rest.utils.IdBoundary;

public interface ExtendedElementService extends ElementService{
	
	public void bindChildToParent(String managerDomain, String managerEmail, String elementDomain, String elementId, IdBoundary childId);
	public Set<ElementBoundary> getChildren(String userDomain, String userEmail, String elementDomain, String elementId);
	public ElementBoundary getParent (String userDomain, String userEmail, String elementDomain, String elementId);
}
