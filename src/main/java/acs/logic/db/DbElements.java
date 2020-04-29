package acs.logic.db;

import java.util.Date;
import java.util.List;
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
import acs.data.elements.CreatedByEntity;
import acs.data.elements.ElementEntity;
import acs.logic.element.ElementConverter;
import acs.logic.element.ElementService;
import acs.rest.element.boundaries.ElementBoundary;

@Service
public class DbElements implements ElementService {

	private String projectName;
	private ElementDao elementDao;
	private ElementConverter converter;
	
	@Value("${spring.application.name:ofir.cohen}")
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Autowired
	public DbElements(ElementDao elementDao, ElementConverter converter) {
		this.converter = converter;
		this.elementDao = elementDao;
	}
	
	@PostConstruct
	public void init() {
		// initialize object after injection
		System.err.println("Project : " + this.projectName + " initialized ElementServiceMockup");
		
	}
	
	@Override
	@Transactional
	public ElementBoundary create(String managerDomain, String managerEmail, ElementBoundary element) {
		
		ElementEntity entity = this.converter.toEntity(element);
		
		String id = UUID.randomUUID().toString();
		
		entity.setElementId(new ElementIdEntity(this.projectName, id));
		
		entity.setCreatedTimestamp(new Date());
		
		entity.setCreatedBy(new CreatedByEntity (new UserIdEntity (managerDomain, managerEmail)));
		
		// select + insert / update
		return this.converter.fromEntity(this.elementDao.save(entity));
	}

	@Override
	@Transactional
	public ElementBoundary update(String managerDomain, String managerEmail, String elementDomain, String elementId,
			ElementBoundary update) {
		
		
		ElementEntity entity = this.elementDao.findById(new ElementIdEntity(elementDomain, elementId)).orElseThrow(
				() -> new RuntimeException("DB No Element With Id " + elementId));
	
	
		if(update.getType() != null  && update.getType().trim().length() != 0) {
			entity.setType(update.getType()); 
		}
		else {
			throw new RuntimeException("ElementEntity invalid type");
		}
		
		if(update.getName() != null && update.getName().trim().length() != 0) {
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
				  this.elementDao.findById(new ElementIdEntity(elementDomain, elementId))
				 .orElseThrow(() -> new RuntimeException("DB No Element With ID"))
				 );
	}
	
	
	@Override
	@Transactional
	public void deleteAllElements(String adminDomain, String adminEmail) {
		//Invoke delete database
		this.elementDao.deleteAll();
	}
}
