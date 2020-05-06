package acs.rest.users;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import acs.logic.user.UserService;
import acs.rest.utils.UserIdBoundary;

@RestController
public class UserController {
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	

	// login valid user and retrieve user details
		@RequestMapping(path = "/acs/users/login/{userDomain}/{userEmail}", 
				method = RequestMethod.GET, 
				produces = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary login(@PathVariable("userDomain") String userDomain,
				@PathVariable("userEmail") String userEmail) {
			return this.userService.login(userDomain, userEmail);
		}

		// create new user to the data base
		@RequestMapping(path = "/acs/users", 
				method = RequestMethod.POST, 
				produces = MediaType.APPLICATION_JSON_VALUE, 
				consumes = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary createUser(@RequestBody UserNewDetails user) {
			UserBoundary userBoundary = new UserBoundary(new UserIdBoundary(null, user.getEmail()), user.getUsername() , user.getRole(), user.getAvatar());
			return this.userService.createUser(userBoundary);
		}

		// update user details
		@RequestMapping(path = "/acs/users/{userDomain}/{userEmail}",
				method = RequestMethod.PUT,
				consumes = MediaType.APPLICATION_JSON_VALUE)
		public void updateUser(@PathVariable("userDomain") String userDomain, @PathVariable("userEmail") String userEmail,
				@RequestBody UserBoundary update) {

			this.userService.updateUser(userDomain, userEmail, update);

		}
		
		 // DUMMY DELETE METHOD - COPY TO ADMINCONTROLLER OR DELETE IF NEEDED
		 // Delete (DELETE) all users
		@RequestMapping(path = "/acs/users/admin/{adminDomain}/{adminEmail}",
				method = RequestMethod.DELETE)
		public void deleteAllUsers(@PathVariable("adminDomain") String adminDomain,
				 					  @PathVariable("adminEmail") String adminEmail) {
			this.userService.deleteAllUsers(adminDomain, adminEmail);	
		}

}
