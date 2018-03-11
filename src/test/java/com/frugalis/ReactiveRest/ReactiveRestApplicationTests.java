package com.frugalis.ReactiveRest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.frugalis.ReactiveRest.entity.Users;
import com.frugalis.ReactiveRest.repository.UserRepository;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactiveRestApplicationTests {
	@Autowired
	private WebTestClient webTestClient;
	@Autowired
	private UserRepository userRepository;
	
		@Test
	public void saveUser() {
		Users ps=new Users("dsld", "dfdf");
		userRepository.deleteAll().subscribe();

		webTestClient.post().uri("/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
                 .body(Mono.just(ps), Users.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString())
				.expectBody()
				.jsonPath("$.name").isEqualTo("dsld");      
               
	}

		@Test
		public void testUser() {
			webTestClient.get().uri("/users")
					.exchange()
					.expectStatus().isOk()
					.expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString())
					.expectBodyList(User.class).hasSize(1);
				     
	               
		}
}
