package br.com.livoltek.core.api.keycloak.dto;

import lombok.Builder;

@Builder
public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        Boolean emailVerified,
        String username
) {}