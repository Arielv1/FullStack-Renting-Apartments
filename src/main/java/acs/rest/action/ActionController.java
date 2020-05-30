package acs.rest.action;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import acs.logic.action.ActionService;
import acs.logic.element.ElementService;
import acs.logic.element.ExtendedElementService;
import acs.rest.element.boundaries.ElementBoundary;

@RestController
public class ActionController {
	
	private final int DEFAULT_PAGE_SIZE = 10;
	private final int DEFAULT_PAGE_NUM = 0;
	private final String EXPECTED_SEARCH_FIELDS[] = {"type", "name"};
	private ActionService actionService;
	private ExtendedElementService elementService;
	
	@Autowired
	public void setActionService(ActionService actionService, ExtendedElementService elementService) {
		this.actionService = actionService;
		this.elementService = elementService;
	}


	// Invoke an action (POST)
	@RequestMapping(path = "/acs/actions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object invokeAnAction(@RequestBody ActionBoundary action) {
		
		if(action.getType().equals("searchElementsOfUser")) {
			return this.elementService.searchAllElementsOfUser(action.getInvokedBy().getUserId(), DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
		}
		
		if(action.getType().equals("searchElementsByNameAndType")) {
			String type = "", name = "";
			
			for(Entry<String, Object> es : action.getActionAttributes().entrySet()) {
				if(es.getKey().equals(EXPECTED_SEARCH_FIELDS[0])) {
					type = (String) es.getValue();
				}
				if(es.getKey().equals(EXPECTED_SEARCH_FIELDS[1])) {
					name = (String) es.getValue();
				}
			}
			
			return this.elementService.searchElementsByNameAndType(action.getInvokedBy().getUserId(), 
					name, 
					type, 
					DEFAULT_PAGE_NUM,
					DEFAULT_PAGE_SIZE);
		}
		
		if(action.getType().equals("deleteSpecific")) {
			this.elementService.deleteSpecificElement(action.getInvokedBy().getUserId(), action.getElement().getElementId());
			return null;
		}
				
		return this.actionService.invokeAction(action);
	}

}
