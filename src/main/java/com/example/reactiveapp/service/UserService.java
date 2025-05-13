package com.example.reactiveapp.service;

import com.example.reactiveapp.domains.UserRequest;
import com.example.reactiveapp.domains.UserResponse;
import com.example.reactiveapp.model.User;
import com.example.reactiveapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<UserResponse> postUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.name());
        user.setAge(userRequest.age());
        user.setEmail(userRequest.email());
        user.setStatus(userRequest.status());
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user)
                .map(saved -> new UserResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getAge(), saved.getStatus()));
    }
}
