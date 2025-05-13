package com.example.reactiveapp.controller;

import com.example.reactiveapp.domains.UserRequest;
import com.example.reactiveapp.domains.UserResponse;
import com.example.reactiveapp.model.User;
import com.example.reactiveapp.service.UserService;
import com.example.reactiveapp.service.UserWebClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/users")
@Slf4j
@Tag(name = "Users", description = "User management APIs")
public class UserController {

    private final Map<String, User> userDB = new ConcurrentHashMap<>();

    @Autowired
    UserWebClientService userWebClientService;

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Mono<User>>> getUser(@PathVariable String id) {
        return Mono.justOrEmpty(userWebClientService.fetchUser(id))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public Mono<UserResponse> postUser(@RequestBody UserRequest request) {
        log.info("Saving user: {}", request);
        return userService.postUser(request);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable String id, @RequestBody User user) {
        if (!userDB.containsKey(id)) {
            return Mono.just(ResponseEntity.notFound().build());
        }
        user.setId(id);
        userDB.put(id, user);
        return Mono.just(ResponseEntity.ok(user));
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<User>> patchUser(@PathVariable String id, @RequestBody Map<String, String> updates) {
        User existingUser = userDB.get(id);
        if (existingUser == null) return Mono.just(ResponseEntity.notFound().build());

        updates.forEach((key, value) -> {
            switch (key) {
                case "name": existingUser.setName(value); break;
                case "email": existingUser.setEmail(value); break;
            }
        });
        return Mono.just(ResponseEntity.ok(existingUser));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id) {
        return Mono.justOrEmpty(userDB.remove(id))
                .map(u -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
