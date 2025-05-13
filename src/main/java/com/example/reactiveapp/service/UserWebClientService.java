package com.example.reactiveapp.service;

import com.example.reactiveapp.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserWebClientService {

    private final WebClient webClient = WebClient.create("http://localhost:8080");

    public Mono<User> fetchUser(String id) {
        return webClient.get()
                .uri("/users/" + id)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<User> createUser(User user) {
        return webClient.post()
                .uri("/users")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class);
    }




}
