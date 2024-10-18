package br.com.livoltek.core.api.keycloak.usecase;

import java.util.UUID;

public interface CreateUserKeycloak {
    UUID execute(String firstName,
                 String lastName,
                 String email,
                 Boolean emailVerified,
                 String username);
}
