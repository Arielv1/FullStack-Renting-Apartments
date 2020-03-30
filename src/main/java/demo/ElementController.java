package demo;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElementController {
	
	// Create new Element
	@RequestMapping(path = "/acs/elements/{managerDomain}/{managerEmail}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary createNewElement(@PathVariable("managerDomain") String managerDomain ,
		     								@PathVariable("managerEmail") String managerEmail,
		     								@RequestBody ElementBoundary eb) {
		return new ElementBoundary(eb.getElementId(), eb.getType(), eb.getName(), eb.isActive(), new Date(), eb.getCreatedBy(),
				eb.getLocation(), eb.getElementAttribues());
	}
	
	//Update an Element
	@RequestMapping(path = "/acs/elements/{managerDomain}/{managerEmail}/{elementDomain}/{elementID}",
			method = RequestMethod.PUT,			
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateElement(@PathVariable("managerDomain") String managerDomain ,
		     				  @PathVariable("managerEmail") String managerEmail,
		     				  @PathVariable("elementDomain") String elementDomain,
		     				  @PathVariable("elementID") String elementID,
		     				  @RequestBody ElementBoundary eb) {
		System.err.println(eb);
	}
	
	
	// Get a specific Element 
	@RequestMapping(path = "/acs/elements/{managerDomain}/{managerEmail}/{elementDomain}/{elementID}",
					method = RequestMethod.GET,		
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary getElement(@PathVariable("managerDomain") String managerDomain,
			  						  @PathVariable("managerEmail") String managerEmail,
			  						  @PathVariable("elementDomain") String elementDomain,
			  						  @PathVariable("elementID") String elementID) {
		Map <String, Object> elementDomainMapping = new HashMap <String, Object>();
		elementDomainMapping.put("managerDomain", managerDomain);
		elementDomainMapping.put("managerEmail", managerEmail);
		
		Map <String, Object> managerDomainMapping = new HashMap <String, Object>();
		managerDomainMapping.put("elementDomain", elementDomain);
		managerDomainMapping.put("elementID", elementID);
		
		return new ElementBoundary(Collections.singletonMap("ID", 1), "demoType", "demoName", false, new Date(), 
				Collections.singletonMap("email", "2020B.Ofir.Cohen"), Collections.singletonMap("lat", "00.00"), 
				Collections.singletonMap("demoAttribute" , "demoValue"));
	}
	private Map <String, Object> elementId;
	private String type;
	private String name;
	private boolean active;	
	private Date createdTimeStamp;
	private Map <String, Object> createdBy;
	private Map <String, Object> location; 
	private Map <String, Object> elementAttribues;
	
	// Get all elements 
	@RequestMapping(path = "/acs/elements/{managerDomain}/{managerEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary[] getManyMessages (@PathVariable("managerDomain") String managerDomain,
											  @PathVariable("managerEmail") String managerEmail) {
		Map <String, Object> elementDomainMapping = new HashMap <String, Object>();
		elementDomainMapping.put("managerDomain", managerDomain);
		elementDomainMapping.put("managerEmail", managerEmail);
		
		return IntStream.range(0, 10) // Stream of integers
					.mapToObj(i -> "message #" + i) // Stream of Strings
					.map(msg -> new ElementBoundary(Collections.singletonMap("domain", "thisDomain"), "Apartment", "idk", false,
													new Date(), elementDomainMapping, null, Collections.singletonMap("demoAttribute", "demoValue"))) // Stream of MessageBoundary
					.collect(Collectors.toList()) // List of MessageBoundary
					.toArray(new ElementBoundary[0]); // Array of MessageBoundary
	}
}
