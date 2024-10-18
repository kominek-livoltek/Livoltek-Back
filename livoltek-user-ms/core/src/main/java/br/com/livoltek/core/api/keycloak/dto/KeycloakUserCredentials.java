package br.com.livoltek.core.api.keycloak.dto;

import lombok.Builder;

@Builder
public record KeycloakUserCredentials(
        String type,
        Boolean temporary,
        String value) {


}
