package acs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import acs.rest.element.boundaries.ElementBoundary;
import acs.rest.utils.IdBoundary;
import acs.rest.utils.UserIdBoundary;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ActionTests {

	private int port;
	private RestTemplate restTemplate;
	private String url;

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + this.port + "/acs/actions";
		this.restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setup() {
		// do nothing
	}

	@Test
	public void testContext() {
	}

	@Test
	public void testPostNewActionReturnActionWithId() throws Exception {
		// GIVEN server is up

		// WHEN I POST /acs/actions AND send action boundary

		ActionBoundary input = new ActionBoundary(new IdBoundary("ofir", null), "Type",
				new ActionElementBoundary(new IdBoundary("ofir", "78")), new Date(),
				new InvokedByBoundary(new UserIdBoundary("ofir", "ofirco26@gmail.com")), null);

		ActionBoundary output = this.restTemplate.postForObject(this.url, input, ActionBoundary.class);

		// THEN the server returns status 2xx
		// AND retrieves a massage with non null id

		if (output.getActionId().getId() == null) {
			throw new Exception("expected non null id but id was null");
		}
	}

	@Test
	public void testPostNewActionReturnActionWithElemetIdNotNull() throws Exception {
		// GIVEN server is up

		// WHEN I POST /acs/actions AND send action boundary

		ActionBoundary input = new ActionBoundary(new IdBoundary("ofir", null), "Type",
				new ActionElementBoundary(new IdBoundary("ofir", "78")), new Date(),
				new InvokedByBoundary(new UserIdBoundary("ofir", "ofirco26@gmail.com")), null);

		ActionBoundary output = this.restTemplate.postForObject(this.url, input, ActionBoundary.class);

		// THEN the server returns status 2xx
		// AND retrieves an action with non null id in element

		if (output.getElement().getElementId() == null) {
			throw new Exception("expected non null id for elemet but id was null");
		}

	}

	@Test
	public void testPostNewActionReturnActionWithSameType() throws Exception {
		// GIVEN server is up

		// WHEN I POST /samples AND send a Action boundary

		ActionBoundary input = new ActionBoundary(new IdBoundary("ofir", null), "update",
				new ActionElementBoundary(new IdBoundary("ofir", "54")), new Date(),
				new InvokedByBoundary(new UserIdBoundary("ofir", "ofirco26@gmail.com")), null);

		ActionBoundary output = this.restTemplate.postForObject(this.url, input, ActionBoundary.class);

//		 THEN the server returns status 2xx
//		 AND retrieves a action with same Type as sent to server
		if (!output.getType().equals(input.getType())) {
			throw new Exception("expected update type to input but received: " + output.getType());
		}

	}

	@Test
	public void testPostNewActionReturnActionWithUpdaedDate() throws Exception {
		// GIVEN server is up

		// WHEN I POST /samples AND send a Action boundary

		ActionBoundary input = new ActionBoundary(new IdBoundary("ofir", null), "update",
				new ActionElementBoundary(new IdBoundary("ofir", "54")), new Date(),
				new InvokedByBoundary(new UserIdBoundary("ofir", "ofirco26@gmail.com")), null);

		ActionBoundary output = this.restTemplate.postForObject(this.url, input, ActionBoundary.class);

		// THEN the server returns status 2xx
		// AND action a Type with different date as sent to server
		if (output.getCreatedTimestamp().equals(input.getCreatedTimestamp())) {
			throw new Exception("expected update type to input but received: " + output.getCreatedTimestamp());
		}
	}
	
	
	@Test
	public void testInvoke5ActionsCheckThatTheyWereAddedToDatabase() throws Exception{
		// GIVEN - Database contains 5 Actions
		// WHEN - Invoked GET to all A
		// THEN - Confirm database size is 5
		
		// Create 5 Elements for database
		List <ActionBoundary> dbContent = IntStream.range(0, 5) //Stream <Integer> with size of 5 (0,1,2,3,4)
				.mapToObj(n -> "Object #" + n) // Stream<Strings> to Stream <Objects>
				.map(current -> 				// Initialize each object 
				new ActionBoundary (new IdBoundary("ofir", null), "update",
						new ActionElementBoundary(new IdBoundary("ofir", "43")), new Date(),
						new InvokedByBoundary(new UserIdBoundary("ofir", "ofirco26@gmail.com")), null))
				.map(boundary -> //Invoke POST for each object
					this.restTemplate.postForObject(this.url, 
													boundary,
													ActionBoundary.class,
													"1","2"))
				.collect(Collectors.toList());
		
		// Confirm database size == 5
		assertEquals(dbContent.size(), 5);
	}	
}
