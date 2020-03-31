package demo;



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
	@RequestMapping(path = "/acs/users/login/{userDomain}/{email}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundry infoOnUser(@PathVariable("userDomain") String userDomain, @PathVariable("email") String email) {
		if (userDomain != null && !userDomain.trim().isEmpty()) {
			Map<String, Object> userIdMapping = new HashMap<>();
			userIdMapping.put("userDomain", userDomain);
			userIdMapping.put("email", email);
			return new UserBoundry(Collections.singletonMap("email", "2020B.Ofir.Cohen"), "demo user", "client", ";-)");
		}else {
			throw new NameNotFoundException("invalid name");
		}
	}

	//create new user to the data base
	@RequestMapping(path = "/acs/users",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundry postNewUser(@RequestBody UserBoundry user) {
		return new UserBoundry(user.getUserId(), user.getUserName(), user.getRole(), user.getAvatar());
	}
	
	// update user details
	@RequestMapping(path = "/acs/users/{userDomain}/{email}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser (
			@PathVariable("userDomain") String id,
			@PathVariable("email") String email,
			@RequestBody UserBoundry update) {
		
		System.err.println(update);
		
	
	}
	
	
	
	
	
	
	

}
