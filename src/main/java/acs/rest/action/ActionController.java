package acs.rest.action;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import acs.logic.action.ActionService;
import acs.logic.element.ElementService;

@RestController
public class ActionController {
	
	private ActionService actionService;
	
	@Autowired
	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}
	
	// Invoke an action
	@RequestMapping(path = "/acs/actions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object invokeAnAction(@RequestBody ActionBoundary action) {
		return this.actionService.invokeAction(action);
	}
}
