package acs.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

import acs.rest.element.ElementBoundary;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ElementTests {
	
	private int port;
	private RestTemplate restTemplate;
	private String url;
	
	private final static String GET_ALL_URL = "/TestUserDomain/TestUserEmail/";
	private final static String GET_URL = "/TestUserDomain/TestUserEmail/TestElementDomain/";
	private final static String POST_URL = "/TestManagerDomain/TestManagerEmail";
	private final static String UPDATE_URL = "/TestManagerDomain}/TestManagerEmail}/TestElementDomain/";
	private final static String DELETE_ALL_URL =  "/admin/TestAdminDomain/TestAdminEmail/";
	
	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + this.port + "/acs/elements";
		this.restTemplate = new RestTemplate();
	}
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@AfterEach
	public void teardown() {
		this.restTemplate.delete(this.url + DELETE_ALL_URL);
	}
	
	@Test
	public void testContext() {	
	}
	
	
	
	@Test
	public void testPostNewElementThenCheckIfCreatedElementHasSameName() throws Exception {
		// GIVEN - Server is up
		// WHEN -  POST /acs/elements to create new ElementBoundary with specific name
		// THEN - Server creates new ElementBoundary with given name and gives 2xx message
		
		
		ElementBoundary input = new ElementBoundary(null,
													"Type", 
													"testName", 
													true,
													new Date(), 
													null, 
													null ,
													null);
		
		ElementBoundary output = this.restTemplate.postForObject(this.url + POST_URL , input, ElementBoundary.class);
		
		assertEquals(output.getName(), input.getName());
	}

	@Test
	public void testGetSpecificElementWithSpecificAttributesInDatabaseAndValidateObjectReturnedByDatabase() throws Exception{
		
		// GIVEN - server is up and contains a single Element
		// WHEN - invoke GET /acs/elements/{userDomain}/{userEmail}/{elementDomain}/{elementId}
		// THEN - server varifies that the element given has same attributes as element which was created,
		//			return 2xx afterwards
		
		Map <String , Object> elementAttributes = new HashMap<String, Object>();
		elementAttributes.put("value1", 1);
		elementAttributes.put("value2", 2.0);
		elementAttributes.put("value3", "Three");
		elementAttributes.put("value4", Collections.singletonMap("Four", "IV"));
		
		
		ElementBoundary newElementObject = 
				  this.restTemplate
					.postForObject(this.url + POST_URL, 
							new ElementBoundary(null,
									"Type", 
									"Name", 
									true,
									new Date(), 
									null, 
									null,
									elementAttributes),
									ElementBoundary.class);
		
		
		ElementBoundary resultElementObject = this.restTemplate.getForObject(this.url + GET_URL + newElementObject.getKey(),
																ElementBoundary.class,
																newElementObject.getElementAttribues());
		
		assertThat(resultElementObject.getElementAttribues().equals(newElementObject.getElementAttribues()));
	}
	
	@Test
	public void testCreate5ElementsCheckThatTheyWereAddedToDatabaseThenDeleteDatabaseAndCheckThatItIsEmptyAndNotNull() throws Exception{
		// GIVEN - Database contains 5 Elements
		// WHEN - Invoked GET to all Elements , the size of the database will be 5
		// THEN - Delete the entire database , confirm it is not null , is empty and return 2xx message from server
		
		// Create 5 Elements for database
		List <ElementBoundary> dbContent = IntStream.range(0, 5) //Stream <Integer> with size of 5 (0,1,2,3,4)
				.mapToObj(n -> "Object #" + n) // Stream<Strings> to Stream <Objects>
				.map(current -> 				// Initialize each object 
				new ElementBoundary (null,
									"Type", 
									"Name", 
									 true,
									new Date(), 
									null, 
									null,
									null))
				.map(boundary -> //Invoke POST for each object
					this.restTemplate.postForObject(this.url + POST_URL, 
													boundary,
													ElementBoundary.class))
				.collect(Collectors.toList());
		
		// Confirm database size == 5
		assertEquals(dbContent.size(), 5);
		
		// Delete all elements from database
		this.restTemplate.delete(this.url + DELETE_ALL_URL);
		
		// Retrieve all elements from database
		ElementBoundary result[] = this.restTemplate.getForObject(this.url + GET_ALL_URL, ElementBoundary[].class);
		
		// Confirm that the database is empty yet is not null
		assertThat(result).isNotNull().isEmpty();
	}
	
	@Test
	public void idk () throws Exception {
		// GIVEN - Server is up 
		// WHEN - Database contains a single element
		// THEN - Invoke UPDATE method in an attempt to change the elementId attribute , check that the new id 
		// 			has not been updated in the database and server returns 2xx message
		
		// Creating new ElementBoundary and adding it to the database through POST
		ElementBoundary element = this.restTemplate.postForObject(this.url + POST_URL,
																new ElementBoundary(null,
																					"TypeTest4", 
																					"NameTest4", 
																					false,
																					new Date(), 
																					null, 
																					null,
																					null
																					),
																ElementBoundary.class);
		
		// Creating the new elementId to update
		Map <String, Object> newElementId = new HashMap <String, Object>();
		
		newElementId.put("domain", "verybaddomain@doesntwork.uk");
		newElementId.put("ID", 2.3);
		
		element.setElementId(newElementId);
			
		//Invoke the UPDATE method
		this.restTemplate.put(this.url + UPDATE_URL + element.getKey(),
							  element ,
							  newElementId);
		
		
		// Retrieve the entire database content
		ElementBoundary database[] = this.restTemplate.getForObject(this.url + GET_ALL_URL, ElementBoundary[].class);
		
		// Check that the databases' old key was kept 
		assertThat(database[0].getElementId()).isNotEqualTo(element.getElementId());
		
	}
}
