package acs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.PostConstruct;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import acs.data.UserRole;
import acs.rest.users.UserBoundary;
import acs.rest.users.UserNewDetails;
import acs.rest.utils.UserIdBoundary;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserTests {
	
	private int port;
	private RestTemplate restTemplate;
	private String url;
	private String domain;
	
	private final static String GET = "/login/{userDomain}/{userEmail}";  
	private final static String PUT = "/{userDomain}/{userEmail}";
	private final static String DELETE_ALL_USERS = "/admin/TestAdminDomain/TestAdminEmail/";
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:"+ this.port +"/acs/users";
		this.restTemplate = new RestTemplate();
		this.domain = "2020b.ofir.cohen";
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
	public void testPostNewUserReturnUserWithId() throws Exception {
		// GIVEN server is up
		
		// WHEN I POST /users AND send a user boundary
		UserNewDetails input = new UserNewDetails( "tomer32@gmail.com", 
				"tomer test", UserRole.ADMIN, ";[");
		
		UserBoundary output = 
		  this.restTemplate
			.postForObject(
					this.url, 
					input, 
					UserBoundary.class);
		
		// THEN the server returns status 2xx 
		// AND retrieves a user with non null id
		if (output.getUserId() == null) {
			throw new Exception("expected non null id but id was null");
		}
	}
	
	@Test
	public void testPostNewUserReturnUserWithSameEmail() throws Exception {
		// GIVEN server is up
		
		// WHEN I POST /users AND send a user boundary
		UserNewDetails input = new UserNewDetails("tomer32@gmail.com", 
				"tomer test", UserRole.ADMIN, ";[");
		
		UserBoundary output = 
		  this.restTemplate
			.postForObject(
					this.url, 
					input, 
					UserBoundary.class);
		
		// THEN the server returns status 2xx 
		// AND retrieves a user with same email as sent to server
		if (!(output.getUserId().getEmail().equals(input.getEmail()))) {
			throw new Exception("expected simplar message to input but received: " + output.getUserId().getEmail());
		}
	}
	
	
	@Test
	public void testPostNewUserAndValidateTheDatabseContainsUserWithTheSameId() throws Exception {
		// GIVEN server is up
		
		// WHEN I POST /users AND send a user boundary
		UserNewDetails input = new UserNewDetails( "tomer32@gmail.com", 
				"tomer test", UserRole.ADMIN, ";[");
		
		UserBoundary userPost =  this.restTemplate
			.postForObject(
					this.url, 
					input, 
					UserBoundary.class);
		
		
		// THEN server contains a single user in the database
		// AND it's user details  is similar input's
		UserBoundary output = 
		  this.restTemplate
			.getForObject(this.url + GET, UserBoundary.class, this.domain ,input.getEmail());
	

		
		assertThat(output.getUserId()).extracting("domain","email")
		.containsExactly(userPost.getUserId().getDomain(), userPost.getUserId().getEmail());
		

	}
	
	
	@Test
	public void testPostNewUserTheDatabaseContainsUserWithTheSameRole() throws Exception {
		// GIVEN server is up
		
		// WHEN I POST /samples AND send a message boundary
		UserNewDetails input = new UserNewDetails("test23@gmail.com", "testy test", 
				UserRole.MANAGER, ";=}");
		
		UserBoundary userPost = this.restTemplate
			.postForObject(
					this.url, 
					input, 
					UserBoundary.class);
		
		// THEN server contains user with generated role
		UserBoundary output = 
		  this.restTemplate
			.getForObject(
					this.url + GET, 
					UserBoundary.class,
					this.domain, input.getEmail());
		
		assertThat(output.getRole())
			.isNotNull();
		
		
		assertThat(output.getRole())
			.usingRecursiveComparison()
			.isEqualTo(userPost.getRole());
	}

	
	@Test
	public void testUpdateUserDetsilsActuallyUpdateDatabse() throws Exception{
		// GIVEN the server is up AND the database contains a message
		
		UserNewDetails input = new UserNewDetails("test23@gmail.com", "testy test", 
				UserRole.MANAGER, ";=}");
		
		
		UserBoundary inputUser = this.restTemplate.postForObject(this.url, input, UserBoundary.class);
		
		
		// WHEN I update details to be different avatar
		inputUser.setAvatar(";]");
		this.restTemplate
			.put(this.url + PUT, inputUser, this.domain, inputUser.getUserId().getEmail());
		
		// THEN the database is updated with the new avatar
		assertThat(this.restTemplate
				.getForObject(this.url + GET, UserBoundary.class,  this.domain, inputUser.getUserId().getEmail())
				.getAvatar())
			.isEqualTo(";]");
	}
	
	
	
	
	
	
}
