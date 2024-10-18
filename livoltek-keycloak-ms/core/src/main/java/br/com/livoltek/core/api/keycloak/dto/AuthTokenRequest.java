package br.com.livoltek.core.api.keycloak.dto;

import lombok.Builder;

@Builder
public record AuthTokenRequest(
        String grantType,
        String clientId,
        String clientSecret

) {
}
