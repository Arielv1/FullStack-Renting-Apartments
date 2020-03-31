package demo;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
	
	//TODO - Delete all users from system
	@RequestMapping(path = "/acs/admin/users/{adminDomain}/{adminEmail}",
			method = RequestMethod.DELETE)
	public void deleteAllUsers(@PathVariable("adminDomain") String adminDomain,	@PathVariable("adminEmail") String adminEmail) {
		
	}
	
	//TODO - Delete all elements from system
	@RequestMapping(path = "/acs/admin/elements/{adminDomain}/{adminEmail}",
			method = RequestMethod.DELETE)
	public void deleteAllElements(@PathVariable("adminDomain") String adminDomain, @PathVariable("adminEmail") String adminEmail) {
		
	}
	//TODO - Delete all actions from system
	@RequestMapping(path = "/acs/admin/actions/{adminDomain}/{adminEmail}",
			method = RequestMethod.DELETE)
	public void deleteAllActions(@PathVariable("adminDomain") String adminDomain, @PathVariable("adminEmail") String adminEmail) {	
	}
	
	
	//Export all users
	@RequestMapping(path = "/acs/admin/users/{adminDomain}/{adminEmail}",
			method = RequestMethod.GET)
	public UserBoundary[] getAllUsers(@PathVariable("adminDomain") String adminDomain,	@PathVariable("adminEmail") String adminEmail) {
		return null;
	}
	
	//Export all actions
	@RequestMapping(path = "/acs/admin/actions/{adminDomain}/{adminEmail}",
			method = RequestMethod.GET)
	public ActionBoundary[] deleteAllActions(@PathVariable("adminDomain") String adminDomain, @PathVariable("adminEmail") String adminEmail) {
		return null;
	}
}
