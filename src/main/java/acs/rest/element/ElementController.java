package acs.rest.element;
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
	@RequestMapping(path = "/acs/elements/{userDomain}/{userEmail}/{elementDomain}/{elementID}",
					method = RequestMethod.GET,		
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary getElement(@PathVariable("userDomain") String userDomain,
			  						  @PathVariable("userEmail") String userEmail,
			  						  @PathVariable("elementDomain") String elementDomain,
			  						  @PathVariable("elementID") String elementID) {
		Map <String, Object> elementDomainMapping = new HashMap <String, Object>();
		elementDomainMapping.put("elementDomain", elementDomain);
		elementDomainMapping.put("elementID", elementID);
		
		Map <String, Object> userDomainMapping = new HashMap <String, Object>();
		userDomainMapping.put("userDomain", userDomain);
		userDomainMapping.put("userEmail", userEmail);
		
		return new ElementBoundary(Collections.singletonMap("ID", 1), "demoType", "demoName", false, new Date(), 
				Collections.singletonMap("email", "2020B.Ofir.Cohen"), Collections.singletonMap("lat", 31.32), 
				Collections.singletonMap("demoAttribute" , "demoValue"));
	}
	
	// Get all elements 
	@RequestMapping(path = "/acs/elements/{userDomain}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary[] getManyMessages (@PathVariable("userDomain") String userDomain,
											  @PathVariable("userEmail") String userEmail) {
		Map <String, Object> elementDomainMapping = new HashMap <String, Object>();
		elementDomainMapping.put("userDomain", userDomain);
		elementDomainMapping.put("userEmail", userEmail);
		
		return IntStream.range(0, 10) // Stream of integers
					.mapToObj(i -> "message #" + i) // Stream of Strings
					.map(msg -> new ElementBoundary(Collections.singletonMap("domain", "thisDomain"), "Apartment", "idk", false,
													new Date(), elementDomainMapping, null, Collections.singletonMap("demoAttribute", "demoValue"))) // Stream of MessageBoundary
					.collect(Collectors.toList()) // List of ElementBoundary
					.toArray(new ElementBoundary[0]); // Array of ElementBoundary
	}
}
