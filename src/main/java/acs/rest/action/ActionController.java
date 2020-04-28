package acs.rest.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import acs.logic.action.ActionService;
import acs.logic.element.ElementService;
import acs.rest.element.boundaries.ElementBoundary;

@RestController
public class ActionController {

	private ActionService actionService;

	@Autowired
	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	// Invoke an action (POST)
	@RequestMapping(path = "/acs/actions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object invokeAnAction(@RequestBody ActionBoundary action) {
		return this.actionService.invokeAction(action);
	}

	// GET all actions
//	@RequestMapping(path = "/acs/admin/actions/{adminDomain}/{adminEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//public ActionBoundary[] getAllActions(@PathVariable("adminDomain") String adminDomain,
	//		@PathVariable("adminEmail") String adminEmail) {
	//	return this.actionService.getAllActions(adminDomain, adminEmail).stream().toArray(i -> new ActionBoundary[i]);
	//}

	// DELETE all actions in the system
	// @RequestMapping(path = "/acs/admin/actions/{adminDomain}/{adminEmail}",
	// method = RequestMethod.DELETE)
	// public void deleteAllActions(@PathVariable("adminDomain") String adminDomain,
	// @PathVariable("adminEmail") String adminEmail) {
	// this.actionService.deleteAllActions(adminDomain, adminEmail);
	// }

}
