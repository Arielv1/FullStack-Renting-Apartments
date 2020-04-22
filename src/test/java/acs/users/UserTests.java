package acs.users;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserTests {
	
	private int port;
	private RestTemplate restTemplate;
	private String url;
	
	
	@PostConstruct
	public void init() {
		// TODO - this.url = "http://localhost:" + this.port + "ENTER RELEVENT URL HERE";
		this.restTemplate = new RestTemplate();
	}
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@Test
	public void testContext() {
		
	}
}
