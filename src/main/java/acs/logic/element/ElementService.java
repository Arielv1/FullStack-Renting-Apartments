package acs.logic.element;

import java.util.List;
import java.util.Map;
import acs.rest.element.ElementBoundary;


public interface ElementService {
	public ElementBoundary create(String managerDomain, String managerEmail, ElementBoundary element);
	public ElementBoundary update(String managerDomain, String managerEmail, String elementId, String elementDomain, ElementBoundary update);
	public List <ElementBoundary> getAll(String userDomain, String userEmail);
	public ElementBoundary getSpecific(String userDomain, String userEmail, String elementDomain, String elementId);
	public void deleteAll(String adminDomain, String adminEmail);

}
