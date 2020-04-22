package acs.logic.element;


import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import acs.data.*;
import acs.rest.element.ElementBoundary;

@Service
public class ElementServiceMockup implements ElementService {
	
	private String projectName;
	
	private Map <String, ElementEntity> database;
	
	private ElementConverter converter;
	
	@Value("${spring.application.name:ofir.cohen}")
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Autowired
	public ElementServiceMockup(ElementConverter converter) {
		this.converter = converter;
	}
	
	@PostConstruct
	public void init() {
		// initialize object after injection
		System.err.println("Project : " + this.projectName + " initialized ElementServiceMockup");
		
		// make sure that this is actually the proper Map for this application
		this.database = Collections.synchronizedMap(new HashMap<>()); 
	}
	
	@Override
	public ElementBoundary create(String managerDomain, String managerEmail, ElementBoundary element) {
		/* Create elementId attribute:
		 *  "elementId": {
    			"domain" : "2020B.Ofir.Cohen"
        		"ID": 1
    		}
		 */
		Map<String, Object> elementId = new HashMap<String, Object>();
		elementId.put("domain", this.projectName);
		elementId.put("id", UUID.randomUUID().toString());
			
		/* Create createdBy attribute:
		 * "createdBy": {
    			"userid":{
    				"domain:"2020b.ofir.cohen",
        			"email": "ofir.cohen@gmail.com"
        		}
    		} 
		 */
		Map<String, Object> createdBy = new HashMap<String, Object>();
				
		Map<String, Object> managerDetails = new HashMap<String, Object>();
		managerDetails.put("domain", managerDomain);
		managerDetails.put("email", managerEmail);
				
		createdBy.put("UserId", managerDetails);
		
		element.setKey(this.projectName);
		element.setElementId(elementId);
		element.setCreatedBy(createdBy);
		
		
		/*
		 * Create key attribute to identify the element : key = id + "!" + domain
		 */
		
		Object keyParts[] = element.getElementId().values().toArray();
		String key = (String)keyParts[0] + "!" + (String)keyParts[1];
		
		ElementEntity entity = this.converter.boundaryToEntity(element);
		
		entity.setElementId(elementId);
		entity.setCreatedBy(createdBy);
		
		// TODO - remove 'key' element is turns out to be irrelevent
		entity.setKey(key);
		
		this.database.put(key , entity);	
		
		return this.converter.entityToBoundary(entity);
	}

	@Override
	public ElementBoundary update(String managerDomain, String managerEmail, String elementDomain, String elementId,
			ElementBoundary update) {
		
		ElementEntity entity = this.database.get(elementId);
		
		if(entity == null) throw new RuntimeException("No element with the given ID");
		
		if(update.getType() != null) entity.setType(update.getType()); 
		else throw new RuntimeException("ElementEntity invalid type");
		
		if(update.getName() != null) 	entity.setName(update.getName()); 
		else throw new RuntimeException("ElementEntity invalid name");
		
		if (update.getActive() != null) entity.setActive(update.getActive());
		else entity.setActive(false);
		
		entity.setLocation(update.getLocation());
		
		entity.setElementAttribues(update.getElementAttribues());
		
		update = this.converter.entityToBoundary(entity);

		return update;
	}

	@Override
	public List <ElementBoundary> getAll(String userDomain, String userEmail) {
		
		return this.database
				.values() // Collection<ElementEntity>
				.stream()  // Stream<ElementEntity>
				.map(this.converter::entityToBoundary) // Convert to Stream<ElementBoundary>
				.collect(Collectors.toList()); // List<ElementBoundary>
	}

	@Override
	public ElementBoundary getSpecificElement(String userDomain, String userEmail, String elementDomain, String elementId) {
		
		ElementBoundary boundary = this.converter.entityToBoundary(this.database.get(elementId));
		
		if(boundary == null) throw new RuntimeException("No element with the given ID");
		else return boundary;
	}
	
	
	@Override
	public void deleteAllElements(String adminDomain, String adminEmail) {
		
		// TODO - check admin privileges
		
		this.database.clear();
	}
}
