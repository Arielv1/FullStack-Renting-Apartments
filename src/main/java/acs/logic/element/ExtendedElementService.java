package acs.logic.element;

import java.util.List;
import java.util.Set;

import acs.rest.element.boundaries.ElementBoundary;
import acs.rest.utils.IdBoundary;

public interface ExtendedElementService extends ElementService{
	
	public void bindChildToParent(String managerDomain, String managerEmail, String elementDomain, String elementId, IdBoundary childId);
	public Set<ElementBoundary> getChildren(String userDomain, String userEmail, String elementDomain, String elementId, int page, int size);
	public Set<ElementBoundary> getParent (String userDomain, String userEmail, String elementDomain, String elementId, int page, int size);
	
	public List <ElementBoundary> getAll(String userDomain, String userEmail, int page, int size);
	public List<ElementBoundary> searchElementsByName(String userDomain, String userEmail, String name, int page, int size);
	public List<ElementBoundary> searchElementsByType(String userDomain, String userEmail, String type, int page, int size);
	public List<ElementBoundary> searchElementsByLocation(String userDomain, String userEmail,
			  double lat_start, double lat_end, double lng_start ,double lng_end , int page, int size);

//	public List<ElementBoundary> searchElementsByLocation(String userDomain, String userEmail,
//														  double lat, double lng, double distance , int page, int size);
	

}
