package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJpaDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void givenDBUser_whenRevokeToken_thenAuthorized() {
		String accessToken = obtainAccessToken("client", "lukasz", "lukasz");

		assertNotNull(accessToken);

		System.out.println(accessToken);
	}

	private String obtainAccessToken(String clientId, String username, String password) {
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", "password");
		params.put("client_id", clientId);
		params.put("username", username);
		params.put("password", password);
		Response response = RestAssured.given()
				.auth()
				.preemptive()
				.basic(clientId, "secret")
				.and()
				.with()
				.params(params)
				.when()
				.post("http://localhost:8080/oauth/token");

		return response.jsonPath().getString("access_token");
	}

}
