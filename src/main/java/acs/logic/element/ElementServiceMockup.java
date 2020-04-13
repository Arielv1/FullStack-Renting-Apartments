package acs.logic.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import acs.data.*;
import acs.rest.element.ElementBoundary;

@Service
public class ElementServiceMockup implements ElementService{
	
	private String projectName;
	
	private Map <String, ElementEntity> database;
	
	private ElementConverter converter;
	
	@Value("${spring.application.name:No_Project_Name}")
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
		System.err.println("project nawme: " + this.projectName);
		
		// make sure that this is actually the proper Map for this application
		this.database = Collections.synchronizedMap(new HashMap<>()); 
	}
	
	@Override
	public ElementBoundary create(String managerDomain, String managerEmail, ElementBoundary element) {
		
		Object keyParts[] = element.getElementId().values().toArray();
		String key = (String)keyParts[0]+"!"+(String)keyParts[1];
		
		this.database.put(key , this.converter.boundaryToEntity(element));	
		
		System.err.println(this.database.size() + " " + key);
		
		return new ElementBoundary(element.getElementId(), 
				element.getType(), 
				element.getName(), 
				element.getActive(), 
				element.getCreatedTimestamp(), 
				element.getCreatedBy(), 
				element.getLocation(), 
				element.getElementAttribues());
	}

	@Override
	public ElementBoundary update(String managerDomain, String managerEmail, String elementId, String elementDomain,
			ElementBoundary update) {
		
		ElementEntity entity = this.database.get(elementId);
		
		if (entity == null)
			return null;
		
		if(update.getType() != null) 	entity.setType(update.getType()); 
		else entity.setType(" ");
		
		if(update.getName() != null) 	entity.setName(update.getName()); 
		else entity.setName(" ");
		
		if (update.getActive() != null) entity.setActive(update.getActive());
		else entity.setActive(false);
		
		entity.setCreatedTimestamp(update.getCreatedTimestamp());
		
		entity.setLocation(update.getLocation());
		
		entity.setElementAttribues(update.getElementAttribues());
		
		update = this.converter.entityToBoundary(entity);

		return update;
	}

	@Override
	public List <ElementBoundary> getAll(String userDomain, String userEmail) {
		
		List <ElementBoundary> boudaries = new ArrayList <ElementBoundary>();
		
		List <ElementEntity> entities = new ArrayList <ElementEntity>(this.database.values());
		
		for (ElementEntity entity : entities) 
			boudaries.add(this.converter.entityToBoundary(entity));
	
		return boudaries;
	}

	@Override
	public ElementBoundary getSpecific(String userDomain, String userEmail, String elementDomain, String elementId) {
		return this.converter.entityToBoundary(this.database.get(elementId));
	}
	
	
	@Override
	public void deleteAll(String adminDomain, String adminEmail) {
		
		// TODO - Check admin privileges
		this.database.clear();
	}
}
