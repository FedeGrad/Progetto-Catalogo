package it.progetto.catalogo.test;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.progetto.catalogo.impl.LoginRequest;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ControllerTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemp;

	@Test
	protected String getAdminToken() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserName("federico");
		loginRequest.setPassword("fox");
		HttpEntity<LoginRequest> login = new HttpEntity<LoginRequest>(loginRequest);
		String jwt = restTemp.postForObject("http://localhost"+port+ "/apiauth/login/jwt", loginRequest, String.class);
		log.info(jwt);
		return jwt;
	}
	
	@Test
	void getAllLibri() {
		String jwt = getAdminToken();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", "Bearer " + jwt);
		HttpEntity<String> jwtEntity = new HttpEntity<String>(header);
		ResponseEntity<String> re = 
				restTemp.exchange("http://localhost"+port+ "/libro/getAllLibri", HttpMethod.GET, jwtEntity, String.class);
		assertThat(re.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	}
	
	@Test
	void getAllLibriKo() {
		String jwt = getAdminToken();
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", "");
		HttpEntity<String> jwtEntity = new HttpEntity<String>(header);
		ResponseEntity<String> re = 
				restTemp.exchange("http://localhost"+port+ "/libro/getAllLibri", HttpMethod.GET, jwtEntity, String.class);
		assertThat(re.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
	}

}
