package br.com.livoltek.core.internal.keycloak.usecase;

import br.com.livoltek.core.api.common.service.HttpService;
import br.com.livoltek.core.api.keycloak.dto.CreateKeycloakUserRequest;
import br.com.livoltek.core.api.keycloak.dto.CreateKeycloakUserResponse;
import br.com.livoltek.core.api.keycloak.dto.KeycloakUserCredentials;
import br.com.livoltek.core.api.keycloak.usecase.CreateKeycloakUser;
import br.com.livoltek.core.api.keycloak.usecase.GetKeycloakToken;
import br.com.livoltek.core.api.properties.KeycloakProperties;
import br.com.livoltek.core.internal.common.dto.HttpRequest;
import br.com.livoltek.core.internal.common.exception.GenericException;
import br.com.livoltek.core.internal.common.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateKeycloakUserDefault implements CreateKeycloakUser {

    private final KeycloakProperties keycloakProperties;
    private final GetKeycloakToken getKeycloakToken;
    private final HttpService httpService;

    @Override
    public UUID execute(String firstName, String lastName, String email, Boolean emailVerified, String username) {
        String bearerToken = getKeycloakToken.execute();
        String createUserUrl = keycloakProperties.urlEndPointUsers();
        CreateKeycloakUserRequest payload = generateRequest(firstName, lastName, email,
                emailVerified, username);
        HttpRequest<CreateKeycloakUserRequest, CreateKeycloakUserResponse> request
                = new HttpRequest<>(createUserUrl, payload, bearerToken);
        try {
            return ObjectUtils.getUUIDFromLocationHeader(httpService.post(request,
                    CreateKeycloakUserResponse.class)
                    .getLocation());
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    private CreateKeycloakUserRequest generateRequest(String firstName, String lastName,
                                                     String email, Boolean emailVerified,
                                                     String username) {
        return CreateKeycloakUserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .emailVerified(emailVerified)
                .enabled(Boolean.TRUE.toString())
                .username(username)
                .credentials(List.of(
                        new KeycloakUserCredentials("password",
                                true,
                                keycloakProperties.defaultPassword())))
                .build();
    }
}