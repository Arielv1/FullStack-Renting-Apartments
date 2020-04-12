package acs.rest.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import acs.rest.action.ActionBoundary;
import acs.rest.users.UserBoundary;

//consumes =  type of input
//produces =  type of output
@RestController
public class AdminController {
	// Delete all users in the system

	@RequestMapping(path = "/acs/admin/users/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	public void delete_AllUsers(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail

	) {}

	// Delete all elements in the system
	@RequestMapping(path = "/acs/admin/elements/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	public void delete_AllElements(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail) {}
	
	// Delete all actions in the system
	@RequestMapping(path = "/acs/admin/actions/{adminDomain}/{adminEmail}", method = RequestMethod.DELETE)
	public void delete_AllActions(@PathVariable("adminDomain") String adminDomain,
			@PathVariable("adminEmail") String adminEmail) {}

	// Export all users
	@RequestMapping (path = "/acs/admin/users/{adminDomain}/{adminEmail}",
					 method = RequestMethod.GET,
					 produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exports_AllUsers (
	@PathVariable("adminDomain") String adminDomain,
	 @PathVariable("adminEmail") String adminEmail){
		Map <String, Object> userId = new HashMap <String, Object>();
		userId.put("domain", "2020b.ofir.cohen");
		userId.put("email", "MichaelHamami@gmail.com");
		return IntStream.range(0, 5) //stream of integers  // we using stream but we can use for as well
				.mapToObj(i -> "User #" + (i+1))  //stream of strings
				.map(msg -> new UserBoundary(userId,"Demo User","PLAYER",";-)")
						) // stream of UserBoundary
				.collect(Collectors.toList()) // list of UserBoundary
				.toArray(new UserBoundary[0]); //UserBoundary[]
	}

	// Export all actions
	@RequestMapping (path = "/acs/admin/actions/{adminDomain}/{adminEmail}",
			 method = RequestMethod.GET,
			 produces = MediaType.APPLICATION_JSON_VALUE)
public ActionBoundary[] exports_AllActions (
	@PathVariable("adminDomain") String adminDomain,
	 @PathVariable("adminEmail") String adminEmail) {
		Map <String, Object> actionId = new HashMap <String, Object>();
		actionId.put("domain", "2020b.ofir.cohen");
		actionId.put("id", "971");
		
		Map <String, Object> elementID = new HashMap <String, Object>();
		elementID.put("domain", "2020b.ofir.cohen");
		elementID.put("id", "54");
		
		Map <String, Object> element = new HashMap <String, Object>();
		element.put("elementId", elementID);
		
		Map <String, Object> userID = new HashMap <String, Object>();
		userID.put("domain", "2020b.ofir.cohen");
		userID.put("email", "MichaelHamami@gmail.com");
		
		Map <String, Object> invokedBy = new HashMap <String, Object>();
		invokedBy.put("userId", userID);
		
		Map <String, Object> actionAttributes = new HashMap <String, Object>();
		actionAttributes.put("key1", "can be set to any value you wish");
		actionAttributes.put("key2", 44.5);
		actionAttributes.put("booleanValue", false);
		actionAttributes.put("lastKey", "it can contain anything you wish");

		
return IntStream.range(0, 5) //stream of integers  // we using stream but we can use for as well
		.mapToObj(i -> "Action #" + (i+1))  //stream of strings
		.map(msg -> new ActionBoundary(
				actionId,"actionType",element,new Date(),invokedBy,actionAttributes)
				) // stream of ActionBoundary
		.collect(Collectors.toList()) // list of ActionBoundary
		.toArray(new ActionBoundary[0]); //ActionBoundary[]
}
}
