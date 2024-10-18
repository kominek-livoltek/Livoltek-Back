package br.com.livoltek.core.api.keycloak.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateUserKeycloakRequest(
        String firstName,
        String lastName,
        String email,
        Boolean emailVerified,
        String enabled,
        String username,
        List<CredencialUserKeycloak> credentials
) {
}
