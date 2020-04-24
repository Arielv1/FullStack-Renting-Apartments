package acs.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	
	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + this.port + "/acs/elements";
		this.restTemplate = new RestTemplate();
	}
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
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
		
		ElementBoundary output = this.restTemplate.postForObject(this.url+"/managerDomain/managerEmail", input, ElementBoundary.class);
		
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
					.postForObject(this.url + "/managerDomain/managerEmail", 
							new ElementBoundary(null,
									"Type", 
									"Name", 
									true,
									new Date(), 
									null, 
									null,
									elementAttributes),
									ElementBoundary.class);
		
		
		ElementBoundary resultElementObject = this.restTemplate.getForObject(this.url +"/userDomain/userEmail/elementDomain/" + newElementObject.getKey(),
																ElementBoundary.class,
																newElementObject.getElementAttribues());
		
		assertThat(resultElementObject.getElementAttribues().equals(newElementObject.getElementAttribues()));
	}
}
