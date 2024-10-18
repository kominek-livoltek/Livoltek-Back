package br.com.livoltek.core.api.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateKeycloakUserRequest(
        String firstName,
        String lastName,
        String email,
        Boolean emailVerified,
        String enabled,
        String username,
        List<KeycloakUserCredentials> credentials
) {
}
