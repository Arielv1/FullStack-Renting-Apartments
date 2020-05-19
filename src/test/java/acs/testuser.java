package acs;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import acs.data.utils.UserRole;
import acs.rest.users.UserBoundary;
import acs.rest.users.UserNewDetails;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class testuser {

	private int port;
	private RestTemplate restTemplate;
	private String url;

	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + this.port + "/acs/";
		this.restTemplate = new RestTemplate();
	}

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@BeforeEach
	@AfterEach
	public void setUp() {
		UserBoundary admin = this.restTemplate.postForObject(this.url + "/users",
				new UserNewDetails("adminx@gmail.com", UserRole.PLAYER, "x test", ";["), UserBoundary.class);
		this.restTemplate.delete(this.url + "/admin/users/{domain}/{email}", admin.getUserId().getDomain(),
				admin.getUserId().getEmail());
	}

	@Test
	public void checkExportAllWithPagination2() throws Exception {

		List<UserBoundary> users = new ArrayList<>();
		for (int i = 1; i <= 100; i++) {
			users.add(this.restTemplate.postForObject(this.url + "/users",
					new UserNewDetails("x" + i + "@gmail.com", UserRole.PLAYER, "x test" + i, ";["),
					UserBoundary.class));
		}
	}

}
