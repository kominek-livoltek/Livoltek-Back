package br.com.livoltek.core.api.keycloak.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(
        Long id,
        String name,
        String email,
        UUID keycloakId
) {}
