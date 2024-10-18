package br.com.livoltek.core.api.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchKeycloakUserResponse(
        @JsonProperty("id")
        String existantUserId
) {
}
