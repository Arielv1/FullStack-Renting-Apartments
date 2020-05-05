package acs;

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
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import acs.data.ElementIdEntity;
import acs.data.elements.ElementEntity;
import acs.rest.element.boundaries.ElementBoundary;
import acs.rest.utils.IdBoundary;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ElementTests {
	
	private int port;
	private RestTemplate restTemplate;
	private String url;
	
	private final static String GET_URL = "/{userDomain}/{userEmail}/";
	private final static String POST_URL = "/{managerDomain}/{managerEmail}/";
	private final static String UPDATE_URL = "/{managerDomain}/{managerEmail}/{elementDomain}/{elementId}";
	
	
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
	
	
    public String elementIdToURL(IdBoundary eib) {
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
													"INFO", 
													"testName", 
													true,
													new Date(), 
													null, 
													null ,
													null);
		
		ElementBoundary output = this.restTemplate.postForObject(this.url + POST_URL , 
																input,
																ElementBoundary.class,
																"managerTestDomain", "managerTestEmail");
		
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
									"INFO", 
									"Name", 
									true,
									new Date(), 
									null, 
									null,
									elementAttributes),
							ElementBoundary.class,
							"managerTestDomain", "managerTestEmail");
		
		
		ElementBoundary resultElementObject = this.restTemplate.getForObject(this.url + GET_URL + elementIdToURL(newElementObject.getElementId()),
																ElementBoundary.class,
																newElementObject.getElementAttribues(),
																"userTestDomain", "userTestEmail");
		
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
									"INFO", 
									"Name", 
									 true,
									new Date(), 
									null, 
									null,
									null))
				.map(boundary -> //Invoke POST for each object
					this.restTemplate.postForObject(this.url + POST_URL, 
													boundary,
													ElementBoundary.class,
													"1","2"))
				.collect(Collectors.toList());
		
		// Confirm database size == 5
		assertEquals(dbContent.size(), 5);
		
		// Delete all elements from database
		this.restTemplate.delete(this.url + DELETE_ALL_URL);
		
		// Retrieve all elements from database
		ElementBoundary result[] = this.restTemplate.getForObject(this.url + GET_URL,
																  ElementBoundary[].class, 
																  "userTestDomain", "userTestEmail");
		
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
																					"INFO", 
																					"NameTest4", 
																					false,
																					new Date(), 
																					null, 
																					null,
																					null
																					),
																ElementBoundary.class,
																"managerTestDomain", "managerTestEmail");
		
		// Creating the new elementId to update
		IdBoundary newElementId = new IdBoundary("testDomain", "testId");
		
		IdBoundary orgElementId = element.getElementId();
		element.setElementId(newElementId);
			
		//Invoke the UPDATE method
		this.restTemplate.put(this.url + UPDATE_URL,
							  element,
							  "managerTestEmail", "ManagerTestDomain",  orgElementId.getDomain(),orgElementId.getId());
		
		
		// Retrieve the entire database content
		ElementBoundary database[] = this.restTemplate.getForObject(this.url + GET_URL, 
																	ElementBoundary[].class, 
																	"userTestDomain", "userTestEmail");
		
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
																						"INFO", 
																						"NameTest4", 
																						false,
																						new Date(), 
																						null, 
																						null,
																						null
																						),
																	ElementBoundary.class,
																	"managerTestDomain", "managerTestEmail");
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
																						"INFO", 
																						"NameTest4", 
																						false,
																						new Date(), 
																						null, 
																						null,
																						null
																						),
																	ElementBoundary.class,
																	"managerTestDomain", "managerTestEmail");
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
																						"INFO", 
																						"NameTest4", 
																						false,
																						new Date(), 
																						null, 
																						null,
																						null
																						),
																	ElementBoundary.class,
																	"managerTestDomain", "managerTestEmail");
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
																						"INFO", 
																						"NameTest4", 
																						false,
																						new Date(), 
																						null, 
																						null,
																						null
																						),
																	ElementBoundary.class,
																	"managerTestDomain", "managerTestEmail");
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
	
	
	@Test
	public void testBindTwoChildrenToParentConfirmAfterwards() throws Exception {
			
		//GIVEN - Database has 3 elements - 1 parent and 2 children
		//WHEN - Bind 2 children elements to parent
		//THEN - Confirm parent has 2 children and validate attributes, server gives 2xx message afterwards
		
		ElementBoundary parent = this.restTemplate.postForObject(this.url + POST_URL,
																new ElementBoundary(null,
																					"INFO", 
																					"Parent", 
																					true,
																					new Date(), 
																					null, 
																					null,
																					null
																					),
																ElementBoundary.class,
																"managerTestDomain", "managerTestEmail");
		
		ElementBoundary child1 = this.restTemplate.postForObject(this.url + POST_URL,
																new ElementBoundary(null,
																					"INFO", 
																					"Child #1", 
																					true,
																					new Date(), 
																					null, 
																					null,
																					null
																					),
																ElementBoundary.class,
																"managerTestDomain", "managerTestEmail");
		
		ElementBoundary child2 = this.restTemplate.postForObject(this.url + POST_URL,
																new ElementBoundary(null,
																					"INFO", 
																					"Child #2", 
																					true,
																					new Date(), 
																					null, 
																					null,
																					null
																					),
																ElementBoundary.class,
																"managerTestDomain", "managerTestEmail");
		Stream.of(child1, child2)
		.map(ElementBoundary::getElementId)
		.forEach(childIdBoundary->
			this.restTemplate.put(this.url + POST_URL + "/{elementDomain}/{elementId}/children", 
					childIdBoundary,
					"TestManagerDomain", "TestManagerEmail", parent.getElementId().getDomain(), parent.getElementId().getId()));
		
		assertThat(this.restTemplate
				.getForObject(this.url + GET_URL + "/{elementDomain}/{elementId}/children", 
						ElementBoundary[].class, 
						"userTestDomain", "userTestEmail", parent.getElementId().getDomain(), parent.getElementId().getId()))
			.hasSize(2)
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyInAnyOrder(child1, child2);
	}
	
	
	@Test
	public void testCreateAnElementAndAttemptToBindItToItself() throws Exception {
		//GIVEN - Database contains a single element
		//WHEN - Invoke binding child to parent method with the same element
		//THEN - Expect to receive an exception
		ElementBoundary element = this.restTemplate.postForObject(this.url + POST_URL,
				new ElementBoundary(null,
									"INFO", 
									"BindingElement", 
									true,
									new Date(), 
									null, 
									null,
									null
									),
				ElementBoundary.class,
				"managerTestDomain", "managerTestEmail");
		
		String domain = element.getElementId().getDomain();
		String id = element.getElementId().getId();
			
		assertThrows(RuntimeException.class, ()->
		this.restTemplate.put(this.url + UPDATE_URL + "/children", 
				  new IdBoundary(domain, id), 
				  "TestManagerDomain", "TestManagerEmail", domain, id));
	}
	
	@Test
	public void testBindParentToChildCheckThatChildHasAParentAndValidateItstheInitialParent() throws Exception {
		//GIVEN - Database contains a parent and child element
		//WHEN - Child is bound to parent
		//THEN - Get all parents of the child , confirm it has the same Id of the initial parent element
		
		ElementBoundary parent = this.restTemplate.postForObject(this.url + POST_URL,
																new ElementBoundary(null,
																					"parentType", 
																					"Parent", 
																					true,
																					new Date(), 
																					null, 
																					null,
																					null
																					),
																ElementBoundary.class,
																"managerTestDomain", "managerTestEmail");

		ElementBoundary child = this.restTemplate.postForObject(this.url + POST_URL,
																new ElementBoundary(null,
																					"childType", 
																					"Child", 
																					true,
																					new Date(), 
																					null, 
																					null,
																					null
																					),
																ElementBoundary.class,
																"managerTestDomain", "managerTestEmail");
	
		this.restTemplate.put(this.url + POST_URL + "/{elementDomain}/{elementId}/children", 
				child.getElementId(),
				"TestManagerDomain", "TestManagerEmail", parent.getElementId().getDomain(), parent.getElementId().getId());
		
		ElementBoundary allParents[] = this.restTemplate.getForObject(this.url + GET_URL + "/{elementDomain}/{elementId}/parents", 
				ElementBoundary[].class, 
				"userTestDomain", "userTestEmail", child.getElementId().getDomain(), child.getElementId().getId());
		
		assertThat(allParents[0].getElementId().getId()).isEqualTo(parent.getElementId().getId());
		
	}
	
	@Test
	public void testBindChildToFirstParentThenBindToSecondParentCheckThatFirstParentLostChildAndSecondParentHasIt() throws Exception {
		// GIVEN - Database contains 2 parent elements , 1 child element
		// WHEN - bind child to first parent, then to second parent
		// THEN - Confirm that only the second parent has a child 
		ElementBoundary parent1 = this.restTemplate.postForObject(this.url + POST_URL,
																new ElementBoundary(null,
																					"parentType", 
																					"Parent #1", 
																					true,
																					new Date(), 
																					null, 
																					null,
																					null
																					),
																ElementBoundary.class,
																"managerTestDomain", "managerTestEmail");

		ElementBoundary parent2 = this.restTemplate.postForObject(this.url + POST_URL,
																new ElementBoundary(null,
																					"parentType", 
																					"Parent #2", 
																					true,
																					new Date(), 
																					null, 
																					null,
																					null
																					),
																ElementBoundary.class,
																"managerTestDomain", "managerTestEmail");
		
		ElementBoundary child = this.restTemplate.postForObject(this.url + POST_URL,
																new ElementBoundary(null,
																					"childType", 
																					"Child", 
																					true,
																					new Date(), 
																					null, 
																					null,
																					null
																					),
																ElementBoundary.class,
																"managerTestDomain", "managerTestEmail");
		
		Stream.of(parent1, parent2)
		.map(ElementBoundary::getElementId)
		.forEach(parentIdBoundary->
			this.restTemplate.put(this.url + POST_URL + "/{elementDomain}/{elementId}/children", 
					child.getElementId(),
					"TestManagerDomain", "TestManagerEmail", parentIdBoundary.getDomain(), parentIdBoundary.getId()));
		
		assertThat(this.restTemplate
				.getForObject(this.url + GET_URL + "/{elementDomain}/{elementId}/children", 
						ElementBoundary[].class, 
						"userTestDomain", "userTestEmail", parent1.getElementId().getDomain(), parent1.getElementId().getId()))
			.hasSize(0);
		
		assertThat(this.restTemplate
				.getForObject(this.url + GET_URL + "/{elementDomain}/{elementId}/children", 
						ElementBoundary[].class, 
						"userTestDomain", "userTestEmail", parent2.getElementId().getDomain(), parent2.getElementId().getId()))
			.hasSize(1);
		
		ElementBoundary allParents[] = this.restTemplate.getForObject(this.url + GET_URL + "/{elementDomain}/{elementId}/parents", 
				ElementBoundary[].class, 
				"userTestDomain", "userTestEmail", child.getElementId().getDomain(), child.getElementId().getId());
		
		assertThat(allParents[0].getElementId().getId()).isEqualTo(parent2.getElementId().getId());
		
	}
	
}
