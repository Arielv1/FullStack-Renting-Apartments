package acs.rest.users;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	// login valid user and retrieve user details
	@RequestMapping(path = "/acs/users/login/{userDomain}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary infoOnUser(@PathVariable("userDomain") String userDomain, @PathVariable("userEmail") String userEmail)  {
		if (userDomain != null && !userDomain.trim().isEmpty()) {
			Map<String, Object> userIdMapping = new HashMap<>();
			userIdMapping.put("userDomain", userDomain);
			userIdMapping.put("userEmail", userEmail);
			return new UserBoundary(Collections.singletonMap("email", "2020B.Ofir.Cohen"), "demo user", "client", ";-)");
		}else {
			throw new RuntimeException("invalid name");
		}
	}

	//create new user to the data base
	@RequestMapping(path = "/acs/users",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary postNewUser(@RequestBody UserBoundary user) {
		return new UserBoundary(user.getUserId(), user.getUserName(), user.getRole(), user.getAvatar());
	}
	
	// update user details
	@RequestMapping(path = "/acs/users/{userDomain}/{userEmail}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser (
			@PathVariable("userDomain") String id,
			@PathVariable("userEmail") String userEmail,
			@RequestBody UserBoundary update) {
		
		System.err.println(update);
		
	
	}
	
	
	
	
	
	
	

}
