package acs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashMap;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import acs.data.utils.UserRole;
import acs.rest.action.ActionBoundary;
import acs.rest.action.boundaries.ActionElementBoundary;
import acs.rest.action.boundaries.InvokedByBoundary;
import acs.rest.element.boundaries.ElementBoundary;
import acs.rest.users.UserBoundary;
import acs.rest.users.UserNewDetails;
import acs.rest.utils.IdBoundary;
import acs.rest.utils.UserIdBoundary;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminTests {
	
	private int port;
	private RestTemplate restTemplate;
	private String url;

	
	private final static String DELETE_ALL_ELEMENTS_URL =  "admin/elements/TestAdminDomain/tomer32@gmail.com/";
	private final static String DELETE_ALL_ACTIONS_URL =  "admin/actions/TestAdminDomain/tomer32@gmail.com/";
	private final static String DELETE_ALL_USERS_URL =  "admin/users/TestAdminDomain/tomer32@gmail.com/";
	private final static String GET_ALL_ELEMENTS_OF_USER = "elements/{userDomain}/{userEmail}";
	private final static String GET_ALL_USERS_URL =  "admin/users/{adminDomain}/{adminEmail}";
	private final static String GET_ALL_ACTIONS_URL =  "admin/actions/TestAdminDomain/tomer32@gmail.com/";	

	private final static String CREATE_USER =  "users";
	private final static String CREATE_ACTION =  "actions";
	private final static String CREATE_ELEMENT =  "elements/{managerDomain}/{managerEmail}";

	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + this.port + "/acs/";
		this.restTemplate = new RestTemplate();
	}
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@AfterEach
	public void teardown() {
		this.restTemplate.delete(this.url + DELETE_ALL_ELEMENTS_URL);
		this.restTemplate.delete(this.url + DELETE_ALL_ACTIONS_URL);
		this.restTemplate.delete(this.url + DELETE_ALL_USERS_URL);

	}
	
	@Test
	public void testContext() {
		
	}
	
	@Test
	public void checkDeleteAllActions() throws Exception {
		// Given the database contains an Actions
		ActionBoundary actionInput = new ActionBoundary(new IdBoundary("domain test", "testId"), "PLAYER",
				new ActionElementBoundary(new IdBoundary("domain element test", "id element test")), new Date(),
//				null, new Date(),
				new InvokedByBoundary(new UserIdBoundary("domain user test", "tomer32@gmail.com")), new HashMap<>());
		
		ActionBoundary action = this.restTemplate.postForObject(this.url + CREATE_ACTION, actionInput,ActionBoundary.class);
		
		// When i delete all Actions
		this.restTemplate
		.delete(this.url + DELETE_ALL_ACTIONS_URL);
		// Then i get no actions when i check it again
		ActionBoundary[] result = this.restTemplate.getForObject(this.url + GET_ALL_ACTIONS_URL, ActionBoundary[].class,"adminDomain","tomer32@gmail.com");

		assertThat(result).isEmpty();
		
	}
	
	@Test
	public void checkDeleteAllUsers() throws Exception {
		// Given the database contains an Users
		// creating user

		UserNewDetails userInput = new UserNewDetails("tomer32@gmail.com", 
				UserRole.ADMIN, "tomer test",  ";[");
		
		UserBoundary user = this.restTemplate.postForObject(this.url + CREATE_USER, userInput , UserBoundary.class);

		
		// When i delete all users
		// deleting users
		this.restTemplate
		.delete(this.url + DELETE_ALL_USERS_URL);
		
		
		// Then i get no users when i check it again
		// getting users again
		UserBoundary[] result = this.restTemplate.getForObject(this.url + GET_ALL_USERS_URL, UserBoundary[].class,"TestAdminDomain","tomer32@gmail.com");
		
		assertThat(result).isEmpty();

		
	}
	
	@Test
	public void checkDeleteAllElements() throws Exception {
		// Given the database contains an Elements
		// Create element
		ElementBoundary input = new ElementBoundary(null,
				"INFO", 
				"testName", 
				true,
				new Date(), 
				null, 
				null ,
				null);

		ElementBoundary output = this.restTemplate.postForObject(this.url + CREATE_ELEMENT , 
							input,
							ElementBoundary.class,
							"managerTestDomain", "tomer32@gmail.com");
		
		// When i delete all Elements
		this.restTemplate
		.delete(this.url + DELETE_ALL_ELEMENTS_URL);
		
		
		// getting elements again
		ElementBoundary[] result = this.restTemplate.getForObject(this.url + GET_ALL_ELEMENTS_OF_USER, ElementBoundary[].class,"userDomain","tomer32@gmail.com");
		
		// Then i get no Elements when i check it again
		assertThat(result).isEmpty();

		
	}
	
	@Test
	public void checkExportAllUsers() throws Exception {
		// Given the database contains an Actions
		// Create user

		UserNewDetails userInput = new UserNewDetails("tomer32@gmail.com", 
				UserRole.ADMIN, "tomer test",  ";[");
		UserBoundary user = this.restTemplate.postForObject(this.url + CREATE_USER,userInput , UserBoundary.class);
		
		// When i get all Users
		UserBoundary[] result = this.restTemplate.getForObject(this.url + GET_ALL_USERS_URL, UserBoundary[].class,"TestAdminDomain","tomer32@gmail.com");

		// Then i check if has same size as i create and check if they equals
		assertThat(result)
		.hasSize(1);
		
		assertThat(result[0])
		.usingRecursiveComparison().isEqualTo(user);

	}
	
	@Test
	public void checkExportAllActions() throws Exception {
		// Given the database contains an Actions
		// create Action
		ActionBoundary actionInput = new ActionBoundary(new IdBoundary("domain test", "testId"), "PLAYER",
				new ActionElementBoundary(new IdBoundary("domain element test", "id element test")), new Date(),
				new InvokedByBoundary(new UserIdBoundary("domain user test", "tomer32@gmail.com")), new HashMap<>());
		
		ActionBoundary action = (ActionBoundary) this.restTemplate.postForObject(this.url + CREATE_ACTION, actionInput,ActionBoundary.class);

		
		// When i get all Actions
		
		ActionBoundary[] result = this.restTemplate.getForObject(this.url + GET_ALL_ACTIONS_URL, ActionBoundary[].class,"TestAdminDomain","tomer32@gmail.com");

		
		// Then i get list of the actions
		assertThat(result)
		.hasSize(1);

		assertThat(result[0])
		.usingRecursiveComparison().isEqualTo(action);
	}
	
	

	@Test
	public void checkExportAllActionsWithPagination() throws Exception {
		// GIVEN the server is up
		// AND the database contains 20 actions

		IntStream.range(0, 20)
				.mapToObj(n -> "Object #" + n) // Stream<Strings> to Stream <Objects>
				.map(current -> // Initialize each object
				new ActionBoundary(new IdBoundary("ofir", null), "update",
						new ActionElementBoundary(new IdBoundary("ofir", "43")), new Date(),
						new InvokedByBoundary(new UserIdBoundary("ofir", "ofirco26@gmail.com")), null))
				.forEach(boundary -> // Invoke POST for each object
				this.restTemplate.postForObject("http://localhost:" + this.port + "/acs/actions", boundary, ActionBoundary.class));

		// WHEN GET samples/byMessagePattern/?size=6&page=3
		ActionBoundary[] actualResults = this.restTemplate.getForObject(
				this.url + GET_ALL_ACTIONS_URL + "?size={size}&page={page}", ActionBoundary[].class, 6, 3);

		// THEN the result contains 2 results
		assertThat(actualResults).hasSize(2);
	}
	
	
	@Test
	public void checkExportAllActionsWithPagination2() throws Exception {
		// GIVEN the server is up
		// AND the database contains 20 actions

		IntStream.range(0, 20)
				.mapToObj(n -> "Object #" + n) // Stream<Strings> to Stream <Objects>
				.map(current -> // Initialize each object
				new ActionBoundary(new IdBoundary("ofir", null), "update",
						new ActionElementBoundary(new IdBoundary("ofir", "43")), new Date(),
						new InvokedByBoundary(new UserIdBoundary("ofir", "ofirco26@gmail.com")), null))
				.forEach(boundary -> // Invoke POST for each object
				this.restTemplate.postForObject("http://localhost:" + this.port + "/acs/actions", boundary, ActionBoundary.class));

		// WHEN GET samples/byMessagePattern/?size=6&page=3
		ActionBoundary[] actualResults = this.restTemplate.getForObject(
				this.url + GET_ALL_ACTIONS_URL + "?size={size}&page={page}", ActionBoundary[].class, 10, 2);

		// THEN the result contains 2 results
		assertThat(actualResults).hasSize(0);
	}
	
	
	@Test
	public void checkExportAllActionsWithPagination3() throws Exception {
		// GIVEN the server is up
		// AND the database contains 20 actions

		IntStream.range(0, 2)
				.mapToObj(n -> "Object #" + n) // Stream<Strings> to Stream <Objects>
				.map(current -> // Initialize each object
				new ActionBoundary(new IdBoundary("ofir", null), "update",
						new ActionElementBoundary(new IdBoundary("ofir", "43")), new Date(),
						new InvokedByBoundary(new UserIdBoundary("ofir", "ofirco26@gmail.com")), null))
				.forEach(boundary -> // Invoke POST for each object
				this.restTemplate.postForObject("http://localhost:" + this.port + "/acs/actions", boundary, ActionBoundary.class));

		// WHEN GET samples/byMessagePattern/?size=6&page=3
		ActionBoundary[] actualResults = this.restTemplate.getForObject(
				this.url + GET_ALL_ACTIONS_URL + "?size={size}&page={page}", ActionBoundary[].class, 3, 0);

		// THEN the result contains 2 results
		assertThat(actualResults).hasSize(2);
	}
	
}
