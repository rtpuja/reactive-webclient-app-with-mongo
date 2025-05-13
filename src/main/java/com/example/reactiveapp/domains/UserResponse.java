package com.example.reactiveapp.domains;

import io.swagger.v3.oas.annotations.media.Schema;

// DTOs
@Schema(description = "User response")
public record UserResponse(String id, String name, String email, int age, String status) {}
