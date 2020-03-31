package demo;

import java.util.stream.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActionController {

	// Invoke an action
	@RequestMapping(path = "/acs/actions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ActionBoundary invokeAnAction(@RequestBody ActionBoundary action) {
		return new ActionBoundary(action.getActionId(), action.getType(), action.getElement(),
				action.getCreatedTimeStamp(), action.getInvokedBy(), action.getActionAttributes());
	}
}
