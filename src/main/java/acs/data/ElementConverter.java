package acs.data;

import java.util.*;

import org.springframework.stereotype.Component;

import acs.rest.element.ElementBoundary;

@Component
public class ElementConverter {
	
	public ElementBoundary entityToBoundary (ElementEntity entity) {
		if (entity == null)
			return null;
		
		return new ElementBoundary(
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
		
		if (boundary.getElementId() != null) {
			
			// Possible to combine in 1 line - need to edit implementaion of setElementId so call setKey
			entity.setElementId(boundary.getElementId());	
			entity.setKey(boundary.getElementId());
		}
		
		
		if(boundary.getType() != null) 	entity.setType(boundary.getType()); 
		else entity.setType(" ");
		
		if(boundary.getName() != null) 	entity.setName(boundary.getName()); 
		else entity.setName(" ");
		
		if (boundary.getActive() != null) entity.setActive(boundary.getActive());
		else entity.setActive(false);
		
		entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		
		entity.setCreatedBy(boundary.getCreatedBy());
		
		entity.setLocation(boundary.getLocation());
		
		entity.setElementAttribues(boundary.getElementAttribues());
		
		return entity;
	}
}
