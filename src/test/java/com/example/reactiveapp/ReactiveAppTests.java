package com.example.reactiveapp;

import com.example.reactiveapp.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.example.reactiveapp.controller.UserController;

@WebFluxTest(controllers = UserController.class)
public class ReactiveAppTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testCreateAndGetUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        webTestClient.post().uri("/users")
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Test User")
                .jsonPath("$.email").isEqualTo("test@example.com");
    }
}
