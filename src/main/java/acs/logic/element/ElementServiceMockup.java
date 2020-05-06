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


import acs.data.*;
import acs.data.elements.CreatedByEntity;
import acs.data.elements.ElementEntity;
import acs.data.elements.LocationEntity;
import acs.data.utils.ElementIdEntity;
import acs.data.utils.UserIdEntity;
import acs.rest.element.boundaries.ElementBoundary;


//@Service
public class ElementServiceMockup implements ElementService {
	
	private String projectName;
	
	private Map <String, ElementEntity> database;
	
	private ElementConverter converter;
	
	@Value("${spring.application.name:2020b.ofir.cohen}")
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
		
		ElementEntity entity = this.converter.toEntity(element);
		
		String id = UUID.randomUUID().toString();
		
		entity.setElementId(new ElementIdEntity(this.projectName, id));
		
		entity.setCreatedTimestamp(new Date());
		
		entity.setCreatedBy(new CreatedByEntity (new UserIdEntity (managerDomain, managerEmail)));
		
		this.database.put(this.projectName + "!" + id, entity);
		
		return this.converter.fromEntity(entity);
	}

	@Override
	public ElementBoundary update(String managerDomain, String managerEmail, String elementDomain, String elementId,
			ElementBoundary update) {
		
		
		ElementEntity entity = this.database.get(elementDomain + "!" + elementId);		
		
		if (entity == null) {
			throw new RuntimeException("There Is No Element With Given ID");
		}
		
		if(update.getType() != null  && !update.getType().trim().isEmpty()) {
			entity.setType(update.getType());
		}
		else {
			throw new RuntimeException("Invalid Element Type");
		}
		
		if(update.getName() != null && !update.getName().trim().isEmpty()) {
			entity.setName(update.getName());
		} 
		else {
			throw new RuntimeException("Invalid Element Name");
		} 
		
		if(update.getActive() == null) {
			entity.setActive(false);
		}
		
		if(update.getLocation() != null) {
			
			try {
				LocationEntity locationToUpdate = new LocationEntity();
				locationToUpdate.setLat(update.getLocation().getLat());
				locationToUpdate.setLng(update.getLocation().getLng());
				entity.setLocation(locationToUpdate);
			}
			catch (Exception e) {
				
			}
				
		}
		
		entity.setElementAttribues(update.getElementAttribues());
		
		update = this.converter.fromEntity(entity);
		
		this.database.put(elementDomain + "!" + elementId, entity);
		
		return update;
	}

	@Override
	public List <ElementBoundary> getAll(String userDomain, String userEmail) {
		
		return this.database
				.values() // Collection<ElementEntity>
				.stream()  // Stream<ElementEntity>
				.map(this.converter::fromEntity) // Convert to Stream<ElementBoundary>
				.collect(Collectors.toList()); // List<ElementBoundary>*/
		
	}

	@Override
	public ElementBoundary getSpecificElement(String userDomain, String userEmail, String elementDomain, String elementId) {
		
		ElementBoundary boundary = this.converter.fromEntity(this.database.get(elementDomain + "!" + elementId));
		
		if(boundary == null) {
			throw new RuntimeException("No element with the given ID");
		}
		else {
			 return boundary;
		}
	}
	
	
	@Override
	public void deleteAllElements(String adminDomain, String adminEmail) {
		
		this.database.clear();
	}
}
