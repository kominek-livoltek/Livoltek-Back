package br.com.livoltek.core.api.keycloak.usecase;

import java.util.Optional;
import java.util.UUID;

public interface SearchKeycloakUser {

    Optional<UUID> execute(String email);

}
