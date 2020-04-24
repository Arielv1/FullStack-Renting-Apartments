package acs.rest.element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import acs.logic.element.ElementService;

@RestController
public class ElementController {	
	
	private ElementService elementService;

	@Autowired
	public void setElementService(ElementService elementService) {
		this.elementService = elementService;
	}
	
		// Create (POST) new element
	@RequestMapping(path = "/acs/elements/{managerDomain}/{managerEmail}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary createNewElement(@PathVariable("managerDomain") String managerDomain ,
		     					  @PathVariable("managerEmail") String managerEmail,
		     					  @RequestBody ElementBoundary element) {
		
		return this.elementService.create(managerDomain, managerEmail, element);
	}
		
		//Update an Element
	@RequestMapping(path = "/acs/elements/{managerDomain}/{managerEmail}/{elementDomain}/{elementId}",
			method = RequestMethod.PUT,			
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateElement(@PathVariable("managerDomain") String managerDomain ,
		     				  @PathVariable("managerEmail") String managerEmail,
		     				  @PathVariable("elementDomain") String elementDomain,
		     				  @PathVariable("elementId") String elementId,
		     				  @RequestBody ElementBoundary eb) {
		eb = this.elementService.update(managerDomain, managerEmail, elementDomain, elementId, eb);
		System.err.println("After Update\n"+eb);
	}
	
		// Get (GET) Specific element by elementId (key)
	@RequestMapping(path = "/acs/elements/{userDomain}/{userEmail}/{elementDomain}/{elementId}",
			method = RequestMethod.GET,		
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary getSpecificElement(@PathVariable("userDomain") String userDomain,
		  						  @PathVariable("userEmail") String userEmail,
		  						  @PathVariable("elementDomain") String elementDomain,
		  						  @PathVariable("elementId") String elementId) {
		return this.elementService.getSpecificElement(userDomain, userEmail, elementDomain, elementId);
	}
		
		//Get All (GET) elements
	@RequestMapping(path = "/acs/elements/{userDomain}/{userEmail}",
			method = RequestMethod.GET,		
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary[] getAllElements(@PathVariable("userDomain") String userDomain,
		  						  @PathVariable("userEmail") String userEmail) {
	
		return this.elementService.getAll(userDomain, userEmail).stream().toArray(i -> new ElementBoundary[i]);
		
	}
}
