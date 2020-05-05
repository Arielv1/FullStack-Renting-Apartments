package acs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import javax.annotation.PostConstruct;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import acs.data.UserRole;
import acs.rest.users.UserBoundary;
import acs.rest.utils.UserIdBoundary;
import acs.rest.utils.UserNameBoundray;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserTests {
	
	private int port;
	private RestTemplate restTemplate;
	private String url;
	
	private final static String GET = "/login/";  
	private final static String DELETE_ALL_USERS = "/admin/TestAdminDomain/TestAdminEmail/";
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:"+ this.port +"/acs/users";
		this.restTemplate = new RestTemplate();
	}
	
	
	@AfterEach
	public void tearDown() {
		this.restTemplate.delete(this.url + DELETE_ALL_USERS);
	}
	
	public String userIdToURL(UserIdBoundary userId) {
		return userId.getDomain() + "/" + userId.getEmail();
	}
	
	
	@Test
	public void testContext() {
		
	}
	
	
	@Test
	public void testCreateNewUserReturnTheUserCreatedWithSpecificName() throws Exception{
		// GIVEN the server is up
		// WHEN - post /users AND send new userBoundary with specific user id
		// THEN - the server return same user id and return 2xx massage
		
		UserBoundary userInput = new UserBoundary(new UserIdBoundary("test.domain", "tomerarnon83@gmail.com"),
				new UserNameBoundray("Test", "name"),
				UserRole.PLAYER,
				":0");
		
		UserBoundary userOutput = this.restTemplate.postForObject(this.url, userInput, UserBoundary.class);
		
		assertEquals(userOutput.getUsername(), userInput.getUsername());
		
		
	}
	
	
	
	@Test
	public void testLoginToUserDetails() throws Exception{
		// GIVEN the server is up 
		// WHEN the user details in the data base
		// THEN the server retrieve user details from the data base AND send 2xx message
		
		UserIdBoundary userId = new UserIdBoundary(null, "tomerarnon83@gmail.com");
		
		
		UserBoundary userInput = this.restTemplate.postForObject(this.url, new UserBoundary(userId,
				new UserNameBoundray("tom", "ron"), 
				UserRole.ADMIN, 
				";("), UserBoundary.class);
		
		UserBoundary resultUser = this.restTemplate.getForObject(this.url + GET + userIdToURL(userInput.getUserId()),
				UserBoundary.class, userInput.getUserId());
		
		assertThat(userInput.getRole().equals(resultUser.getRole()));
		
		
		
	}
	
	
	@Test
	public void testUpdateUserDetails() throws Exception {
		// GIVEN server is up 
		// WHEN user in the data base 
		// THEN the server update the user details according to the input
		
		UserIdBoundary userId = new UserIdBoundary("domain test", "test@gmail.com");
		
		
		UserBoundary userInput = this.restTemplate.postForObject(this.url, new UserBoundary(userId,
				new UserNameBoundray("test", "before"), 
				UserRole.ADMIN, 
				null), UserBoundary.class);
		
			String domain = userInput.getUserId().getDomain();
			String email = userInput.getUserId().getEmail();
			
			 userInput.setUserName(new UserNameBoundray("test", "after"));
			this.restTemplate.put(this.url , userInput, domain, email);
			
			assertThat(this.restTemplate.getForObject(this.url + GET, UserBoundary.class, domain, email).getUsername()
					.equals("testUser"));
		
		
		
		
	}
	
	
	
	
	
	
}
