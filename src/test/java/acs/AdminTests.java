package acs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import acs.rest.action.ActionBoundary;
import acs.rest.action.boundaries.ActionElementBoundary;
import acs.rest.action.boundaries.InvokedByBoundary;
import acs.rest.utils.IdBoundary;
import acs.rest.utils.UserIdBoundary;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminTests {
	
	private int port;
	private RestTemplate restTemplate;
	private String url;
	private String url_actions;

	
	private final static String DELETE_ALL_ELEMENTS_URL =  "/elements/TestAdminDomain/TestAdminEmail/";
	private final static String DELETE_ALL_ACTIONS_URL =  "/actions/TestAdminDomain/TestAdminEmail/";
	private final static String DELETE_ALL_USERS_URL =  "/users/TestAdminDomain/TestAdminEmail/";


	
	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + this.port + "/acs/admin/";
		this.url_actions = "http://localhost:" + this.port + "/acs/actions";
		this.restTemplate = new RestTemplate();
	}
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@AfterEach
	public void teardown() {
//		this.restTemplate.delete(this.url + DELETE_ALL_ELEMENTS_URL);
//		this.restTemplate.delete(this.url + DELETE_ALL_ACTIONS_URL);
//		this.restTemplate.delete(this.url + DELETE_ALL_USERS_URL);

	}
	
	@Test
	public void testContext() {
		
	}
	
	@Test
	public void checkDeleteAllActions() throws Exception {
		// Given the database contains an Actions
		Object action = this.restTemplate.postForObject(this.url_actions, new ActionBoundary(new IdBoundary("domain test", "testId"), "PLAYER",
				new ActionElementBoundary(new IdBoundary("domain element test", "id element test")), new Date(),
//				null, new Date(),

				new InvokedByBoundary(new UserIdBoundary("domain user test", "domain user email")), new HashMap<>()),
				Object.class);
		// When i delete all Actions
		this.restTemplate
		.delete(this.url + DELETE_ALL_ACTIONS_URL);
		// Then i get no actions when i check it again
//		List<ActionBoundary> result = (List<ActionBoundary>) this.restTemplate.getForObject(this.url + "actions/{adminDomain}/{adminEmail}", ActionBoundary.class,"admin Domain"," admin Email");
		ActionBoundary result = this.restTemplate.getForObject(this.url + "actions/{adminDomain}/{adminEmail}", ActionBoundary.class,"admin Domain"," admin Email");

		assertThat(result).isNotNull();
		
	}
	
	@Test
	public void checkExportAllUsers() throws Exception {
		// Given the database contains an Actions
		
		
		
		// When i get all Actions
		
		// Then i get list of the actions
		
	}
	
	@Test
	public void checkDeleteAllUsers() throws Exception {
		// Given the database contains an Actions
		
		// When i delete all Actions
		
		// Then i get no actions when i check it again
		
	}
	
	@Test
	public void checkExportAllActions() throws Exception {
		// Given the database contains an Actions
		
		
		
		// When i get all Actions
		
		// Then i get list of the actions
		
	}
}
