package acs.logic.element;

import java.util.*;
import org.springframework.stereotype.Component;

import acs.data.ElementIdEntity;
import acs.data.UserIdEntity;
import acs.data.elements.ElementEntity;
import acs.rest.element.boundaries.CreatedByBoundary;
import acs.rest.element.boundaries.ElementBoundary;
import acs.rest.utils.IdBoundary;
import acs.rest.utils.UserIdBoundary;


@Component
public class ElementConverter {
   /* "elementId": {
    	"domain" : "2020B.Ofir.Cohen"
        "ID": 1
    },
    "type": "demoType",
    "name": "demoName",
    "active": false,
    "createdTimestamp": "2020-04-01T08:10:44.284+0000",
    "createdBy": {
    	"userid":{
    		"domain:"2020b.ofir.cohen",
        	"email": "ofir.cohen@gmail.com"
        	}
    },
    "location": {
        "lat": "00.00"
    },
    "elementAttribues": {
        "demoAttribute": "demoValue"
    }*/
	public ElementBoundary fromEntity (ElementEntity entity) {
		
		IdBoundary elementIdBoundary = new IdBoundary();
		CreatedByBoundary createdByBoundary = new CreatedByBoundary();
		UserIdBoundary userIdBoundary = new UserIdBoundary();
		
		if (entity.getElementId() != null) {
			elementIdBoundary.setDomain(entity.getElementId().getDomain());
			elementIdBoundary.setId(entity.getElementId().getId());
		}
		else {
			elementIdBoundary = null;
		}
		
		
		if (entity.getCreatedBy() != null && entity.getCreatedBy().getUserId() != null) {
			userIdBoundary.setDomain(entity.getCreatedBy().getUserId().getDomain());
			userIdBoundary.setEmail(entity.getCreatedBy().getUserId().getEmail());
			createdByBoundary.setUserId(userIdBoundary);
		}
		else {
			createdByBoundary = null;
		}
		
		
		return new ElementBoundary(elementIdBoundary,
				entity.getType(),
				entity.getName(),
				entity.getActive(),
				entity.getCreatedTimestamp(),
				createdByBoundary,
				entity.getLocation(),
				entity.getElementAttribues());
	}
	
	public ElementEntity toEntity (ElementBoundary boundary) {
		ElementEntity entity = new ElementEntity();
		
		if (boundary.getElementId() != null) {
			ElementIdEntity elementIdEntity = new ElementIdEntity();
			elementIdEntity.setDomain(boundary.getElementId().getDomain());
			elementIdEntity.setId(boundary.getElementId().getId());
		}
		else {
			entity.setElementId(new ElementIdEntity());
		}
				
		if(boundary.getType() != null && boundary.getType().trim().length() != 0) {
			entity.setType(boundary.getType());
		} 
		else {
			throw new RuntimeException("Invalid ElementBoundary Type");
		}
		
		if(boundary.getName() != null && boundary.getName().trim().length() != 0) {
			entity.setName(boundary.getName());
		} 
		else {
			throw new RuntimeException("Invalid ElementBoundary Name");
		}
		
		if(boundary.getActive() != null) {
			entity.setActive(boundary.getActive());
		} 
		else {
			entity.setActive(true);
		}
		
		
		entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		
		if(boundary.getCreatedBy()!= null && boundary.getCreatedBy().getUserId() != null) {
			UserIdEntity userIdEntity = new UserIdEntity();
			userIdEntity.setDomain(boundary.getCreatedBy().getUserId().getDomain());
			userIdEntity.setEmail(boundary.getCreatedBy().getUserId().getEmail());
		}
		else {
			entity.setCreatedBy(null);
		}
		
		entity.setLocation(boundary.getLocation());
		
		entity.setElementAttribues(boundary.getElementAttribues());
		
		return entity;
	}
}
