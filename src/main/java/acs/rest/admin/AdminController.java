package acs.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import acs.logic.action.ActionService;
import acs.logic.db.DbActionService;
import acs.logic.db.DbElementService;
import acs.logic.db.DbUserService;
import acs.logic.element.ElementService;
import acs.logic.user.UserService;
import acs.rest.action.ActionBoundary;
import acs.rest.users.UserBoundary;

//consumes =  type of input
//produces =  type of output

@RestController
public class AdminController {
	
	
	// ask if should i change to extended Element service
	private UserService userService;
	private ElementService dbElementeService;
	private ActionService actionService;

	
	
	@Autowired
	public AdminController(UserService userService, ElementService dbElementeService,
			ActionService actionService) {
		this.userService = userService;
		this.dbElementeService = dbElementeService;
		this.actionService = actionService;
	}

	// Delete all users in the system
	

	@RequestMapping(path = "/acs/admin/users/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	public void delete_AllUsers(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail

	) {
		this.userService.deleteAllUsers(adminDomain, adminEmail);

	}

	// Delete all elements in the system
	@RequestMapping(path = "/acs/admin/elements/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	public void delete_AllElements(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail) 
	{
		this.dbElementeService.deleteAllElements(adminDomain, adminEmail);
	
	}
	
	// Delete all actions in the system
	@RequestMapping(path = "/acs/admin/actions/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	
	public void delete_AllActions(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail) 
	{
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