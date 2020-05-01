package acs.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import acs.data.ElementIdEntity;
import acs.data.elements.ElementEntity;
import acs.rest.element.boundaries.ElementBoundary;
import acs.rest.utils.ElementIdBoundary;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ElementTests {
	
	private int port;
	private RestTemplate restTemplate;
	private String url;
	
	private final static String GET_ALL_URL = "/TestUserDomain/TestUserEmail/";
	private final static String GET_URL = "/TestUserDomain/TestUserEmail/";
	private final static String POST_URL = "/TestManagerDomain/TestManagerEmail";
	private final static String UPDATE_URL = "/TestManagerDomain}/TestManagerEmail/";
	
	
	// TODO - change url when delete is implemented
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
	
	
	/*"elementId": {
    	"domain" : "2020B.Ofir.Cohen"
        "ID": 1
    },
    "type": "demoType",
    "name": "demoName",
    "active": false,
    "createdTimestamp": "2020-04-01T08:10:44.284+0000",
    "createdBy": {
    	"userid":{
    		"domain:"2020b.ofir.cohen",
        	"email": "ofir.cohen@gmail.com"
        	}
    },
    "location": {
        "lat": "00.00"
    },
    "elementAttribues": {
        "demoAttribute": "demoValue"
    }*/
    public String elementIdToURL(ElementIdBoundary eib) {
		return eib.getDomain() + "/" + eib.getId();
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
		// THEN - server verifies that the element given has same attributes as element which was created,
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
		
		
		ElementBoundary resultElementObject = this.restTemplate.getForObject(this.url + GET_URL + elementIdToURL(newElementObject.getElementId()),
																ElementBoundary.class,
																newElementObject.getElementAttribues());
		
		assertThat(resultElementObject.getElementAttribues().equals(newElementObject.getElementAttribues()));
	}
	
	@Test
	public void testCreate5ElementsCheckThatTheyWereAddedToDatabaseThenDeleteDatabaseAndCheckThatItIsEmptyAndNotNull() throws Exception{
		// GIVEN - Database contains 5 Elements
		// WHEN - Invoked GET to all Elements
		// THEN - Confirm database size is 5,  delete the entire database , confirm it is not null , is empty and return 2xx message from server
		
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
	public void testCreateNewElementAttemptToChangeItsIdAndVerifyThatTheDatabaseHadNotChangedTheId () throws Exception {
		// GIVEN - Database contains an element 
		// WHEN - I change elementAttributes with UPDATE method
		// THEN - Check that the new id has not been updated in the database and server returns 2xx message
		
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
		ElementIdBoundary newElementId = new ElementIdBoundary("testDomain", "testId");
		
		String elementIdentifierURL = elementIdToURL(element.getElementId());
		element.setElementId(new ElementIdBoundary("testdomain", "1"));
			
		//Invoke the UPDATE method
		this.restTemplate.put(this.url + UPDATE_URL + elementIdentifierURL,
							  element ,
							  newElementId);
		
		
		// Retrieve the entire database content
		ElementBoundary database[] = this.restTemplate.getForObject(this.url + GET_ALL_URL, ElementBoundary[].class);
		
		// Check that the databases' old key was kept 
		assertThat(database[0].getElementId()).isNotEqualTo(element.getElementId());
		
	}
	
	@Test
	public void testCreateNewElementThenUpdateItWithNullNameExpectAndCheckForRuntimeException() throws Exception{
		//GIVEN - An element in the database
		//WHEN - Attempt to set the elements name to 'null'
		//THEN - Server receives an exception and return 2xx message
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
		String newName = null;
		element.setName(newName);
		try {
			this.restTemplate.put(this.url + UPDATE_URL + elementIdToURL(element.getElementId()),
				  element ,
				  newName);
			fail("Expected Invalid Name Exception");
		}
		catch (RuntimeException e) {
		}
	}
	
	@Test
	public void testCreateNewElementThenUpdateItWithEmptyNameWith5SpacesExpectAndCheckForRuntimeException() throws Exception{
		//GIVEN - An element in the database
		//WHEN - Attempt to set the elements name to a name with 5 spaces "    "
		//THEN - Server receives an exception and return 2xx message
		
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
		String newName = "     ";
		element.setName(newName);
		try {
			this.restTemplate.put(this.url + UPDATE_URL + elementIdToURL(element.getElementId()),
				  element ,
				  newName);
			fail("Expected Invalid Name Exception");
		}
		catch (RuntimeException e) {
		}
	}
	
	@Test
	public void testCreateNewElementThenUpdateItWithNullTypeExpectAndCheckForRuntimeException() throws Exception{
		//GIVEN - An element in the database
		//WHEN - Attempt to set the elements' type to 'null'
		//THEN - Server receives an exception and return 2xx message
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
		String newType = null;
		element.setType(newType);
		try {
			this.restTemplate.put(this.url + UPDATE_URL + elementIdToURL(element.getElementId()),
				  element ,
				  newType);
			fail("Expected Invalid Type Exception");
		}
		catch (RuntimeException e) {
		}
	}
	
	@Test
	public void testCreateNewElementThenUpdateItWithTypeThatHas3SpacestAndCheckForRuntimeException() throws Exception{
		//GIVEN - An element in the database
		//WHEN - Attempt to set the elements type to "   "
		//THEN - Server receives an exception and return 2xx message
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
		String newType = "   ";
		element.setName(newType);
		try {
			this.restTemplate.put(this.url + UPDATE_URL + elementIdToURL(element.getElementId()),
				  element ,
				  newType);
			fail("Expected Invalid Type Exception");
		}
		catch (RuntimeException e) {
		}
	}
}
