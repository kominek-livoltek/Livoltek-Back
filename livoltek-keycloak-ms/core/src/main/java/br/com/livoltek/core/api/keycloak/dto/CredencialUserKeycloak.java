package br.com.livoltek.core.api.keycloak.dto;

import lombok.Builder;

@Builder
public record CredencialUserKeycloak(
        String type,
        Boolean temporary,
        String value) {


}
