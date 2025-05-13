package com.example.reactiveapp.domains;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User creation request")
public record UserRequest(
        @Schema(description = "User name", example = "Pooja")
        String name,

        @Schema(description = "User age", example = "30")
        int age,

        @Schema(description = "User email", example = "pk@gmail.com")
        String email,

        @Schema(description = "User type", example = "Admin")
        String status
) {}

