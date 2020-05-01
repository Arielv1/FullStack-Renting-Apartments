package acs.rest.admin;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import acs.logic.action.ActionService;
import acs.logic.element.ElementService;
import acs.logic.user.UserService;
import acs.rest.action.ActionBoundary;
import acs.rest.users.UserBoundary;

//consumes =  type of input
//produces =  type of output
@RestController
public class AdminController {
	// Delete all users in the system
	private UserService userService;
	private ElementService elementService;
	private ActionService actionService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setElementService(ElementService elementService) {
		this.elementService = elementService;
	}

	@Autowired
	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	@RequestMapping(path = "/acs/admin/users/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	public void delete_AllUsers(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail) {
		this.userService.deleteAllUsers(adminDomain, adminEmail);
	}

	// Delete all elements in the system
	@RequestMapping(path = "/acs/admin/elements/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	public void delete_AllElements(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail) {
		this.elementService.deleteAllElements(adminDomain, adminEmail);
	}

	// Delete all actions in the system
	@RequestMapping(path = "/acs/admin/actions/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	public void delete_AllActions(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail) {
		this.actionService.deleteAllActions(adminDomain, adminEmail);
	}

	// Export all users
	@RequestMapping(path = "/acs/admin/users/{adminDomain}/{adminEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserBoundary> exports_AllUsers(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail) {
		return this.userService.getAllUsers(adminDomain, adminEmail);
	}

	// Export all actions
	@RequestMapping(path = "/acs/admin/actions/{adminDomain}/{adminEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ActionBoundary> exports_AllActions(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail) {
		return this.actionService.getAllActions(adminDomain, adminEmail);

	}
}