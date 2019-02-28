package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJpaDemoApplicationTests {



	@Test
	public void whenGetActualToken() {
		String accessToken = obtainAccessToken("client", "lukasz", "lukasz");

		assertNotNull(accessToken);
		System.out.println(accessToken);

		int b = getUsersList("client", "lukasz", "lukasz", "http://localhost:8080/user", accessToken );
		assertEquals(200, b);

	}


	@Test
	public void whenTestExpiredToken() {
		String tokenExpired = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTEyMzg3NjQsInVzZXJfbmFtZSI6Imx1a2FzeiIsImF1dGhvcml0aWVzIjpbIkFETUlOIl0sImp0aSI6ImZmOWVmNGVmLTYzY2EtNGI4OC1hMDVmLTIzODliZDNiNDM3YyIsImNsaWVudF9pZCI6ImNsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.C78AZJqAzIakcBszBcdWxMx8UHz_wFnHHcvyM0rMVkA";
		assertEquals(401, getUsersList("client", "lukasz", "lukasz", "http://localhost:8080/user",tokenExpired ));
	}


	@Test
	public void whenActualToken() {
		String tokenActual = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTEzMjgzOTgsInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6IjdhMmQwYTk3LWVhZDMtNDA1My04YzAzLWNkNTg0MDRiZWJjYiIsImNsaWVudF9pZCI6ImNsaWVudCIsInNjb3BlIjpbInJlYWQiXX0.CAAcrDiTx8quHW7J-_wNIlgYr6MQ7CCEwNjaFQkSX7o";
		assertEquals(200, getUsersList("client", "lukasz", "lukasz", "http://localhost:8080/user",tokenActual ));
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



	private int getUsersList(String clientId, String username, String password, String link, String token) {
		Map<String, String> params = new HashMap<>();
//		params.put("grant_type", "password");
//		params.put("client_id", clientId);
//		params.put("username", username);
//		params.put("password", password);
		Response response = RestAssured.given()
				.auth()
				.preemptive()
				.oauth2(token)
				.and()
				.with()
				//.params(params)
				.when()
				.get(link);

		return response.statusCode();
	}

	private int getUsersWithoutToken(String clientId, String username, String password, String link) {

		Response response = RestAssured.given()
				.auth()
				.preemptive()
				.oauth2("wrong token")
				.and()
				.with()
				.when()
				.get(link);

		return response.statusCode();
	}

}
