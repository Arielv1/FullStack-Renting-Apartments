package acs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.persistence.EnumType;

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
import acs.rest.utils.UserNameBoundray;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserTests {
	
	private int port;
	private RestTemplate restTemplate;
	private String url;
	
	private final static String GET = "/login/userDomain/userEmail";  
	private final static String PUT = "/userDomain/userEmail";
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
	public void testPostNewUserReturnUserWithId() throws Exception {
		// GIVEN server is up
		
		// WHEN I POST /users AND send a message boundary
		UserNewDetails input = new UserNewDetails( "tomer32@gmail.com", 
				new UserNameBoundray("tomer", "test"), UserRole.ADMIN, ";[");
		
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
				new UserNameBoundray("tomer", "test"), UserRole.ADMIN, ";[");
		
		UserBoundary output = 
		  this.restTemplate
			.postForObject(
					this.url, 
					input, 
					UserBoundary.class);
		
		// THEN the server returns status 2xx 
		// AND retrieves a message with same message as sent to server
		if (!(output.getUserId().getEmail().equals(input.getEmail()))) {
			throw new Exception("expected simplar message to input but received: " + output.getUserId().getEmail());
		}
	}
	
	/*
	@Test
	public void testPostNewUserAndValidateTheDatabseContainsASingleUserWithTheSameUserId() throws Exception {
		// GIVEN server is up
		
		// WHEN I POST /samples AND send a user boundary
		UserNewDetails input = new UserNewDetails( "tomer32@gmail.com", 
				new UserNameBoundray("tomer", "test"), UserRole.ADMIN, ";[");
		
		this.restTemplate
			.postForObject(
					this.url, 
					input, 
					UserBoundary.class);
		
		// THEN server contains a single user in the database
		// AND it's userId  is similar input's
		UserBoundary[] output = 
		  this.restTemplate
			.getForObject(this.url, UserBoundary[].class);
		
		assertThat(output)
			.hasSize(1);
				
//		assertThat(output[0].getMessage())
//			.isEqualTo(input.getMessage());
		
//		assertThat(output[0])
//			.usingRecursiveComparison()
//			.isEqualTo(input);
		
		assertThat(output[0])
			.extracting(
					"tomer32@gmail.com", 
					new UserNameBoundray("tomer", "test"), UserRole.ADMIN, ";[")
			.containsExactly(
				input.getEmail());

	}
	/*
	
	@Test
	public void testPostNewMessageTheDatabaseContainsMessageWithCreatedId() throws Exception {
		// GIVEN server is up
		
		// WHEN I POST /samples AND send a message boundary
		ComplexMessageBoundary input = new ComplexMessageBoundary("test", null, true, 1, 1.0, new NameBoundary("Jane", "Smith"), null, null);
		
		String id = this.restTemplate
			.postForObject(
					this.url, 
					input, 
					ComplexMessageBoundary.class)
				.getId();
		
		// THEN server contains message with generated id
		ComplexMessageBoundary output = 
		  this.restTemplate
			.getForObject(
					this.url + "/{id}", 
					ComplexMessageBoundary.class,
					id);
		
		assertThat(output)
			.isNotNull();
		
		assertThat(output)
			.isEqualToComparingOnlyGivenFields(input, 
					"message", "critical", "value", "value2");
		
		assertThat(output.getName())
			.usingRecursiveComparison()
			.isEqualTo(input.getName());
	}

	@Test
	public void testUpdateMessageValue2ActuallyUpdateDatabse() throws Exception{
		// GIVEN the server is up AND the database contains a message
		ComplexMessageBoundary input = 
  		  this.restTemplate
			.postForObject(this.url, 
					new ComplexMessageBoundary("test", null, false, 1, 999.99, new NameBoundary("x", "y"), null, null), 
					ComplexMessageBoundary.class);
		
			String id = input 
					.getId();
		
		// WHEN I update value2 of the object to 1.5
		input.setValue2(1.5);
		this.restTemplate
			.put(this.url + "/{id}", input, id);
		
		// THEN the database is updated with the new value
		assertThat(this.restTemplate
				.getForObject(this.url + "/{id}", ComplexMessageBoundary.class, id)
				.getValue2())
			.isEqualTo(1.5);
	}
	
	*/
	
	
	
	
}
