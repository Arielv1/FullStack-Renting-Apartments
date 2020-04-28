package acs.logic.element;

import java.util.*;
import org.springframework.stereotype.Component;
import acs.data.ElementEntity;
import acs.rest.element.boundaries.ElementBoundary;

@Component
public class ElementConverter {
	/*"key" : "2020b.ofir.cohen!123456" <------ might be temp , will possibly removed
    "elementId": {
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
		
		return new ElementBoundary(entity.getElementId(),
				entity.getType(),
				entity.getName(),
				entity.getActive(),
				entity.getCreatedTimestamp(),
				entity.getCreatedBy(),
				entity.getLocation(),
				entity.getElementAttribues());
	}
	
	public ElementEntity toEntity (ElementBoundary boundary) {
		ElementEntity entity = new ElementEntity();
			
		entity.setElementId(boundary.getElementId());
				
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
		
		if(boundary.getName() != null) {
			entity.setName(boundary.getName());
		}
		else {
			entity.setName(" ");
		}
		
		entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		
		entity.setCreatedBy(boundary.getCreatedBy());
		
		entity.setLocation(boundary.getLocation());
		
		entity.setElementAttribues(boundary.getElementAttribues());
		
		return entity;
	}
}
