package acs.rest.admin;

import java.util.Collections;
import java.util.Date;
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
		return IntStream.range(0, 5) //stream of integers  // we using stream but we can use for as well
				.mapToObj(i -> "User #" + (i+1))  //stream of strings
				.map(msg -> new UserBoundary(Collections.singletonMap("domain", "domain_value"),msg,"user role",":)")) // stream of UserBoundary
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
return IntStream.range(0, 5) //stream of integers  // we using stream but we can use for as well
		.mapToObj(i -> "Action #" + (i+1))  //stream of strings
		.map(msg -> new ActionBoundary(Collections.singletonMap("domain", "domain_value"),"actionType",Collections.singletonMap("domain", "domain_value"),new Date(),Collections.singletonMap("invokedby:"+msg, "player@demo"),Collections.singletonMap("action", "information"))) // stream of ActionBoundary
		.collect(Collectors.toList()) // list of ActionBoundary
		.toArray(new ActionBoundary[0]); //ActionBoundary[]
}
}
