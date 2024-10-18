package br.com.livoltek.core.api.user.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        UUID keycloakId
) {}
