package acs.logic.db;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acs.dal.ElementDao;
import acs.data.*;
import acs.logic.element.ElementConverter;
import acs.logic.element.ElementService;
import acs.rest.boundaries.ElementIdBoundary;
import acs.rest.boundaries.UserIdBoundary;
import acs.rest.element.boundaries.CreatedByBoundary;
import acs.rest.element.boundaries.ElementBoundary;

//@Service
public class dbElements implements ElementService {

	
	private String projectName;
	private ElementDao elementDao;
	private ElementConverter converter;
	
	@Value("${spring.application.name:ofir.cohen}")
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Autowired
	public dbElements(ElementDao elementDao, ElementConverter converter) {
		this.converter = converter;
		this.elementDao = elementDao;
	}
	
	@PostConstruct
	public void init() {
		// initialize object after injection
		System.err.println("Project : " + this.projectName + " initialized ElementServiceMockup");
		
	}
	
	@Override
	public ElementBoundary create(String managerDomain, String managerEmail, ElementBoundary element) {
		
ElementEntity entity = this.converter.toEntity(element);
		
		String id = UUID.randomUUID().toString();
		
		entity.setElementId(new ElementIdBoundary(this.projectName, id));
		
		entity.setCreatedTimestamp(new Date());
		
		entity.setCreatedBy(new CreatedByBoundary (new UserIdBoundary (managerDomain, managerEmail)));
		
		// select + insert / update
		return this.converter.fromEntity(this.elementDao.save(entity));
	}

	@Override
	public ElementBoundary update(String managerDomain, String managerEmail, String elementDomain, String elementId,
			ElementBoundary update) {
		
		ElementEntity entity = this.elementDao.findById(elementId).orElseThrow(
				() -> new RuntimeException("DB No Element With Id " + elementId));
	
	
		if(update.getType() != null) {
			entity.setType(update.getType()); 
		}
		else {
			throw new RuntimeException("ElementEntity invalid type");
		}
		
		if(update.getName() != null) {
			entity.setName(update.getName()); 
		}
		else {
			throw new RuntimeException("ElementEntity invalid name");
		}
		
		if (update.getActive() != null) {
			entity.setActive(update.getActive());
		}
		else {
			entity.setActive(false);
		}
		
		entity.setLocation(update.getLocation());
		
		entity.setElementAttribues(update.getElementAttribues());
		
		update = this.converter.fromEntity(entity);

		return update;
	}

	@Override
	@Transactional(readOnly = true)
	public List <ElementBoundary> getAll(String userDomain, String userEmail) {
		// invoke select * from database
		
		return StreamSupport.stream(this.elementDao.findAll().spliterator(), false)
				.map(this.converter :: fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public ElementBoundary getSpecificElement(String userDomain, String userEmail, String elementDomain, String elementId) {
		// invoke select database
		
		return this.converter.fromEntity(
				  this.elementDao.findById(elementId)
				 .orElseThrow(() -> new RuntimeException("DB no element with id"))
				 );
	}
	
	
	@Override
	@Transactional
	public void deleteAllElements(String adminDomain, String adminEmail) {
		//Invoke delete database
		this.elementDao.deleteAll();
	}
}
