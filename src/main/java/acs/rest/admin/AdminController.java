package acs.rest.admin;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import acs.logic.action.ActionService;
import acs.logic.db.DbElements;
import acs.logic.element.ElementService;
import acs.logic.user.UserService;
import acs.logic.user.UserConvertor;
import acs.rest.action.ActionBoundary;
import acs.rest.users.UserBoundary;

//consumes =  type of input
//produces =  type of output
@RestController
public class AdminController {
	
	private UserService userService;
	private DbElements dbElementeService;
	private ActionService actionService;

	
	// Delete all users in the system

	@RequestMapping(path = "/acs/admin/users/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	public void delete_AllUsers(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail

	) {
//		 this.userDao.deleteAll();
		this.userService.deleteAllUsers(adminDomain, adminEmail);

	}

	// Delete all elements in the system
	@RequestMapping(path = "/acs/admin/elements/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	public void delete_AllElements(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail) 
	{
//		 this.elementDao.deleteAll();
		this.dbElementeService.deleteAllElements(adminDomain, adminEmail);
	
	}
	
	// Delete all actions in the system
	@RequestMapping(path = "/acs/admin/actions/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	
	public void delete_AllActions(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail) 
	{
//		this.actionDao.deleteAll();
		this.actionService.deleteAllActions(adminDomain, adminEmail);

	}

	
//	 Export all users
	
	@RequestMapping (path = "/acs/admin/users/{adminDomain}/{adminEmail}",
					 method = RequestMethod.GET,
					 produces = MediaType.APPLICATION_JSON_VALUE)
	
	public UserBoundary[] exports_AllUsers (
	@PathVariable("adminDomain") String adminDomain,
	 @PathVariable("adminEmail") String adminEmail){

	return this.userService.getAllUsers(adminDomain,adminEmail).toArray(new UserBoundary[0]);
	}

		
	// Export all actions
	@RequestMapping (path = "/acs/admin/actions/{adminDomain}/{adminEmail}",
			 method = RequestMethod.GET,
			 produces = MediaType.APPLICATION_JSON_VALUE)
public ActionBoundary[] exports_AllActions (
	@PathVariable("adminDomain") String adminDomain,
	 @PathVariable("adminEmail") String adminEmail) 
	{
	
	return this.actionService.getAllActions(adminDomain,adminEmail).toArray(new ActionBoundary[0]);
	
//		return StreamSupport
//		.stream(
//				// INVOKE SELECT DATABASE 
//				this.actionDao
//					.findAll()
//					.spliterator(),
//				false) //Stream<ActionEntity>
//		.map(this.actionConverter::fromEntity) // Stream<ActionBoundary>
//		.collect(Collectors.toList()) // List<ActionBoundary>
//		.toArray(new UserBoundary [0]); //ActionBoundary[]
}
}