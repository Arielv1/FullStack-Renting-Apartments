package acs.logic.db;

import java.util.Date;
import java.util.List;
import java.util.Set;
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
import acs.data.elements.LocationEntity;
import acs.data.utils.ElementIdEntity;
import acs.data.utils.UserIdEntity;
import acs.logic.EntityNotFoundException;
import acs.logic.element.ElementConverter;
import acs.logic.element.ElementService;
import acs.logic.element.ExtendedElementService;
import acs.rest.element.boundaries.ElementBoundary;
import acs.rest.utils.IdBoundary;
import acs.rest.utils.ValidEmail;

@Service
public class DbElementService implements ExtendedElementService {

	private String projectName;
	private ElementDao elementDao;
	private ElementConverter converter;
	private ValidEmail valid;
	
	@Value("${spring.application.name:ofir.cohen}")
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Autowired
	public DbElementService(ElementDao elementDao, ElementConverter converter, ValidEmail valid) {
		this.converter = converter;
		this.elementDao = elementDao;
		this.valid = valid;
	}
	
	@PostConstruct
	public void init() {
		// initialize object after injection
		System.err.println("Project : " + this.projectName + " initialized DbElementService");
		
	}
	
	@Override
	@Transactional
	public ElementBoundary create(String managerDomain, String managerEmail, ElementBoundary element) {
		
		if (!valid.isEmailVaild(managerEmail)) {
			throw new RuntimeException("Element Post - Invalid Manager Email");
		}
		
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
				() -> new EntityNotFoundException("DB No Element With Id " + elementDomain + "!" + elementId));
	
	
		if(update.getType() != null  && !update.getType().trim().isEmpty()) {
			entity.setType(update.getType()); 
		}
		else {
			throw new RuntimeException("ElementEntity invalid type");
		}
		
		if(update.getName() != null && !update.getName().trim().isEmpty()) {
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
		
		if (update.getLocation() != null) {
			
			LocationEntity locationEntity = new LocationEntity();
			
			if(update.getLocation().getLat() != null) {
				locationEntity.setLat(update.getLocation().getLat());
			}
						
			if(update.getLocation().getLng() != null) {
				locationEntity.setLng(update.getLocation().getLng());
			}
			
			entity.setLocation(locationEntity);
		} 

		entity.setElementAttribues(update.getElementAttribues());
		
		update = this.converter.fromEntity(entity);
		
		this.elementDao.save(entity);
		
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
				 .orElseThrow(() -> new EntityNotFoundException("DB No Element With ID" + elementDomain + "!" + elementId))
				 );
	}
	
	
	@Override
	@Transactional
	public void deleteAllElements(String adminDomain, String adminEmail) {
		//Invoke delete database
	
		this.elementDao.deleteAll();
	}

	@Override
	@Transactional
	public void bindChildToParent(String managerDomain, String managerEmail, String elementDomain , String elementId, IdBoundary childId) {
		
		if(childId == null) {
			throw new EntityNotFoundException("Child Element Isn't Initialized");
		}
		
		ElementEntity parent = this.elementDao.findById(new ElementIdEntity (elementDomain, elementId))
								.orElseThrow(() -> new EntityNotFoundException("DB No Parent With ID" + elementDomain + "!" + elementId));
		
		ElementEntity child = this.elementDao.findById(new ElementIdEntity(childId.getDomain(), childId.getId()))
								.orElseThrow(() -> new EntityNotFoundException("DB No Child With ID" + elementDomain + "!" + elementId));
		
		if(parent.getElementId().equals(child.getElementId())) {
			throw new RuntimeException("Parent and Child share Id - Cannot Bind Element To Itself");
		}
		
		parent.addChild(child);
		this.elementDao.save(parent);
	}

	@Override
	@Transactional(readOnly = true)
	public Set<ElementBoundary> getChildren(String userDomain, String userEmail, String elementDomain , String elementId) {
		
		ElementEntity parent = this.elementDao.findById(new ElementIdEntity (elementDomain , elementId))
								.orElseThrow(() -> new EntityNotFoundException("DB No Parent With ID" + elementDomain + "!" + elementId));
		
		
		return parent.getChildren()
					.stream()
					.map(this.converter::fromEntity)
					.collect(Collectors.toSet());
	}

	@Override
	@Transactional(readOnly = true)
	public ElementBoundary getParent(String userDomain, String userEmail, String elementDomain , String elementId) {
		
		ElementEntity child = this.elementDao.findById(new ElementIdEntity(elementDomain , elementId))
				.orElseThrow(() -> new EntityNotFoundException("DB No Child With ID" + elementDomain + "!" + elementId));
		
		if(child.getParent() != null) {
			return this.converter.fromEntity(child.getParent());
		}
		else {
			return null;
		}
		
	}
}
