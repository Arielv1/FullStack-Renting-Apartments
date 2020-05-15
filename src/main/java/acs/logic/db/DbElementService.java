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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import acs.dal.ElementDao;
import acs.dal.UserDao;
import acs.data.elements.CreatedByEntity;
import acs.data.elements.ElementEntity;
import acs.data.elements.LocationEntity;
import acs.data.users.UserEntity;
import acs.data.utils.ElementIdEntity;
import acs.data.utils.UserIdEntity;
import acs.data.utils.UserRole;
import acs.logic.element.ElementConverter;
import acs.logic.element.ExtendedElementService;
import acs.logic.exceptions.EntityNotFoundException;
import acs.logic.exceptions.ForbiddenAccessException;
import acs.logic.exceptions.ForbiddenActionException;
import acs.rest.element.boundaries.ElementBoundary;
import acs.rest.utils.IdBoundary;
import acs.rest.utils.ValidEmail;

@Service
public class DbElementService implements ExtendedElementService {

	private String projectName;
	
	private ElementDao elementDao;
	private UserDao userDao;
	
	private ElementConverter converter;
	private ValidEmail valid;
	
	@Value("${spring.application.name:ofir.cohen}")
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Autowired
	public DbElementService(ElementDao elementDao, UserDao userDao, ElementConverter converter, ValidEmail valid) {
		this.converter = converter;
		this.elementDao = elementDao;
		this.userDao = userDao;
		this.valid = valid;
	}
	
	@PostConstruct
	public void init() {
		// initialize object after injection
		System.err.println("Project : " + this.projectName + " initialized DbElementService");
		
	}
	
	private UserEntity retrieveUserFromDb (String domain, String email) {
		return this.userDao.findById(new UserIdEntity (domain, email)).orElseThrow(
				() -> new EntityNotFoundException("No User Exists With Domain " + domain + " Email " + email));
	}
	
	private ElementEntity retrieveElementFromDb (String domain, String id) {
		return this.elementDao.findById(new ElementIdEntity(domain, id)).orElseThrow(
				() -> new EntityNotFoundException("DB No Element With Id " + domain + "!" + id));
	}
	
	@Override
	@Transactional
	public ElementBoundary create(String managerDomain, String managerEmail, ElementBoundary element) {
		
		UserEntity manager = retrieveUserFromDb(managerDomain, managerEmail);
		
		if (manager.getRole() == null || !manager.getRole().equals(UserRole.MANAGER)) {
			throw new ForbiddenActionException("User Has No Permission To Create Elements");
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
		
		UserEntity manager = retrieveUserFromDb(managerDomain, managerEmail);
		
		if (manager.getRole() == null || !manager.getRole().equals(UserRole.MANAGER)) {
			throw new ForbiddenActionException("User Has No Permission To Update Elements");
		}
		
		ElementEntity entity = retrieveElementFromDb(elementDomain, elementId);
		
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

		entity.setElementAttributes(update.getElementAttributes());
		
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
	public List<ElementBoundary> getAll(String userDomain, String userEmail,int page, int size) {
		
		UserEntity user = retrieveUserFromDb(userDomain, userEmail);
			
		if(user.getRole().equals(UserRole.MANAGER)) {
			return this.elementDao.findAll(
					PageRequest.of(page, size, Direction.ASC, "elementId")) // paginate according to page and size
					.getContent() 	//List<ElementEntity>
					.stream() 	// Stream<ElementEntity>
					.map(this.converter::fromEntity)	 //Stream<ElementBoundary>
					.collect(Collectors.toList());
		}
		else {
			if (user.getRole().equals(UserRole.PLAYER)) {
				return this.elementDao.findAllByActive(true, PageRequest.of(page, size, Direction.ASC, "elementId")) // List<ElementEntity>
						.stream() // Stream <ElementEntity>
						.map(this.converter::fromEntity) // Stream <ElementBoundary>
						.collect(Collectors.toList()); // List <ElementBoundary>
			}
			else { // user role must be admin by process of elimination 
				throw new ForbiddenActionException("User Doesn't Have Permission To Get Elements");
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ElementBoundary getSpecificElement(String userDomain, String userEmail, String elementDomain, String elementId) {
		// invoke select database
		
		UserEntity user = retrieveUserFromDb(userDomain, userEmail);
		
		ElementEntity element = retrieveElementFromDb(elementDomain, elementId);
		
		if(user.getRole().equals(UserRole.MANAGER) || (user.getRole().equals(UserRole.PLAYER) && element.getActive())) {
			return this.converter.fromEntity(element);		
		}
		else {
			throw new ForbiddenAccessException("User Doesn't Have Access To Get This Element");
		}
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
		
		UserEntity user = retrieveUserFromDb(elementDomain, managerEmail);
		
		if(!user.getRole().equals(UserRole.MANAGER)) {
			throw new ForbiddenActionException("User Doesn't Have Permission To Bind Elements");
		}
		
		if(childId == null) {
			throw new EntityNotFoundException("Child ElementId Isn't Initialized");
		}
		
		ElementEntity parent = retrieveElementFromDb(elementDomain, elementId);
				
		ElementEntity child =  retrieveElementFromDb(childId.getDomain(), childId.getId());
						
		if(parent.getElementId().equals(child.getElementId())) {
			throw new RuntimeException("Parent and Child share Id - Cannot Bind Element To Itself");
		}
		
		parent.addChild(child);
		this.elementDao.save(parent);
	}
		
		// Get all children elements from parentId
	@Override
	@Transactional(readOnly = true)
	public Set<ElementBoundary> getChildren(String userDomain, String userEmail, String elementDomain , String elementId, int page,int size) {
		
		//TODO - check user role and return different elements accordingly 
		
		
		ElementEntity parent = retrieveElementFromDb(elementDomain, elementId);
		
		List <ElementEntity> children = this.elementDao.findAllChildrenByParent_ElementId(parent.getElementId(),
																		   PageRequest.of(page, size,Direction.ASC, "elementId"));
	
		return children.stream()
				.map(this.converter::fromEntity)
				.collect(Collectors.toSet());
	}
		
		// Get parent elemnt from childId
	@Override
	@Transactional(readOnly = true)
	public Set<ElementBoundary> getParent(String userDomain, String userEmail, String elementDomain , String elementId, int page,int size) {
		
		//TODO - check user role and return different elements accordingly
		
		ElementEntity child = retrieveElementFromDb(elementDomain, elementId);
		
		return this.elementDao.findAllParentsByChildren_ElementId
				(child.getElementId(), PageRequest.of(page, size, Direction.ASC, "elementId"))
				.stream()
				.map(this.converter::fromEntity)
				.collect(Collectors.toSet());	
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ElementBoundary> searchElementsByName(String userDomain, String userEmail, String name, int page, int size) {
		
		UserEntity user = retrieveUserFromDb(userDomain, userEmail);
		
		List <ElementEntity> results;
		if(user.getRole().equals(UserRole.MANAGER)) {
			results = this.elementDao.findAllByName(name, PageRequest.of(page, size, Direction.ASC, "elementId"));
		}
		else {
			if (user.getRole().equals(UserRole.PLAYER)) {
				results =  this.elementDao.findAllByNameAndActive(name, true, PageRequest.of(page, size, Direction.ASC, "elementId"));
			}
			else {
				// user role must be admin by process of elimination 
				throw new ForbiddenActionException("User Doesn't Have Permission To Get Elements");
			}
		}
			
		return results.stream()  // Stream <ElementEntity>
				.map(this.converter::fromEntity)  // Stream <ElementBoundary>
				.collect(Collectors.toList()); // List <ElementBoundary>
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<ElementBoundary> searchElementsByType(String userDomain, String userEmail, String type, int page, int size) {
		
		UserEntity user = retrieveUserFromDb(userDomain, userEmail);
		
		List <ElementEntity> results;
		
		if(user.getRole().equals(UserRole.MANAGER)) {
			results = this.elementDao.findAllByType(type, PageRequest.of(page, size, Direction.ASC, "elementId"));
		}
		else {
			if (user.getRole().equals(UserRole.PLAYER)) {
				results =  this.elementDao.findAllByTypeAndActive(type, true, PageRequest.of(page, size, Direction.ASC, "elementId"));
			}
			else {
				// user role must be admin by process of elimination 
				throw new ForbiddenActionException("User Doesn't Have Permission To Get Elements");
			}
		}
		
		return results.stream()  // Stream <ElementEntity>
				.map(this.converter::fromEntity)  // Stream <ElementBoundary>
				.collect(Collectors.toList()); // List <ElementBoundary>
	}

	@Override
	@Transactional(readOnly = true)
	public List<ElementBoundary> searchElementsByLocation(String userDomain, String userEmail, double lat, double lng, double distance , int page, int size) {
		
		//TODO - check user role / email and return different elements accordingly
		
		UserEntity user = retrieveUserFromDb(userDomain, userEmail);
		
		double lat_start = lat - distance;
		double lat_end = lat + distance;
		double lng_start = lng - distance; 
		double lng_end = lng + distance;
		
		List <ElementEntity> results = this.elementDao.findAllBylocationLatBetweenAndLocationLngBetween(lat_start,lat_end,lng_start,lng_end, PageRequest.of(page, size, Direction.ASC, "elementId"));
		
		if(user.getRole().equals(UserRole.MANAGER)) {
			results = this.elementDao.findAllBylocationLatBetweenAndLocationLngBetween(lat_start,
																					   lat_end,
																					   lng_start,lng_end,
																					   PageRequest.of(page, size, Direction.ASC, "elementId"));
		}
		else {
			if (user.getRole().equals(UserRole.PLAYER)) {
				results =  this.elementDao.findAllBylocationLatBetweenAndLocationLngBetweenAndActive(lat_start,
																									lat_end,
																									lng_start,
																									lng_end,
																									true,
																									PageRequest.of(page, size, Direction.ASC, "elementId"));
			}
			else {
				// user role must be admin by process of elimination 
				throw new ForbiddenActionException("User Doesn't Have Permission To Get Elements");
			}
		}
		
		return results.stream()  // Stream <ElementEntity>
				.map(this.converter::fromEntity)  // Stream <ElementBoundary>
				.collect(Collectors.toList());	// List <ElementBoundary>
		}
	
}
