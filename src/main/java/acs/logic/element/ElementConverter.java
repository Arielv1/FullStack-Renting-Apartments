package acs.logic.element;

import java.util.*;

import org.springframework.stereotype.Component;

import acs.data.ElementEntity;
import acs.rest.element.ElementBoundary;

@Component
public class ElementConverter {
	
	public ElementBoundary entityToBoundary (ElementEntity entity) {
		if (entity == null) 
			return null;
		
		return new ElementBoundary(entity.getKey(),
				entity.getElementId(),
				entity.getType(),
				entity.getName(),
				entity.getActive(),
				entity.getCreatedTimestamp(),
				entity.getCreatedBy(),
				entity.getLocation(),
				entity.getElementAttribues());

	}
	
	/*
	Map <String, Object> elementId; --> Can be null
	String type; --> Can't be null
	String name; --> Can't be null
	boolean active; --> Can't be null
	Date createdTimestamp; --> Can be null
	Map <String, Object> createdBy; --> Can be null
	Map <String, Double> location; --> Can be null
	Map <String, Object> elementAttribues; --> Can be null
	*/
	public ElementEntity boundaryToEntity (ElementBoundary boundary) {
		
		ElementEntity entity = new ElementEntity();
		
		if (boundary.getElementId() != null) entity.setElementId(boundary.getElementId());	
		
		// TODO - remove 'key' element is turns out to be irrelevent
		if (boundary.getKey() != null)	entity.setKey(boundary.getKey());
		else entity.setKey();
		
		if(boundary.getType() != null) 	entity.setType(boundary.getType()); 
		else throw new RuntimeException("ElementBoundary invalid type");
		
		if(boundary.getName() != null) 	entity.setName(boundary.getName()); 
		else throw new RuntimeException("ElementBoundary invalid name");
		
		if (boundary.getActive() != null) entity.setActive(boundary.getActive());
		else entity.setActive(false);
		
		entity.setCreatedTimestamp(new Date());
		
		entity.setCreatedBy(boundary.getCreatedBy());
		
		entity.setLocation(boundary.getLocation());
		
		entity.setElementAttribues(boundary.getElementAttribues());
		
		return entity;
	}
}
