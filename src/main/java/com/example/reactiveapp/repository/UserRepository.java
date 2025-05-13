package com.example.reactiveapp.repository;

import com.example.reactiveapp.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
}
