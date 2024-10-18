package br.com.livoltek.core.api.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthTokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("expires_in")
        String expiresIn,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("not_before_policy")
        String notBeforePolicy,
        @JsonProperty("scope")
        String scope
) {
}
