package br.com.livoltek.core.internal.keycloak.usecase;

import br.com.livoltek.core.api.common.service.HttpService;
import br.com.livoltek.core.api.properties.KeycloakProperties;
import br.com.livoltek.core.api.keycloak.dto.CreateUserKeycloakRequest;
import br.com.livoltek.core.api.keycloak.dto.CreateUserKeycloakResponse;
import br.com.livoltek.core.api.keycloak.dto.CredencialUserKeycloak;
import br.com.livoltek.core.api.keycloak.usecase.CreateUserKeycloak;
import br.com.livoltek.core.api.keycloak.usecase.GetToken;
import br.com.livoltek.core.internal.common.dto.HttpRequest;
import br.com.livoltek.core.internal.common.exception.GenericException;
import br.com.livoltek.core.internal.common.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateUserKeycloakDefault implements CreateUserKeycloak {

    private final KeycloakProperties keycloakProperties;
    private final GetToken getToken;
    private final HttpService httpService;

    @Override
    public UUID execute(String firstName, String lastName, String email,
                         Boolean emailVerified, String username) {
        String bearerToken = getToken.execute();
        String createUserUrl = keycloakProperties.urlEndPointUsers();
        CreateUserKeycloakRequest payload = generateRequest(firstName, lastName, email,
                emailVerified, username);
        UUID userId;
        try {
            HttpRequest<CreateUserKeycloakRequest, CreateUserKeycloakResponse> request
                    = new HttpRequest<>(createUserUrl, payload, bearerToken);

            userId = ObjectUtils.getUUIDFromLocationHeader(httpService.post(request,
                            CreateUserKeycloakResponse.class).getLocation());
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
        return userId;
    }

    private CreateUserKeycloakRequest generateRequest(String firstName, String lastName,
                                                     String email, Boolean emailVerified,
                                                     String username) {
        return CreateUserKeycloakRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .emailVerified(emailVerified)
                .enabled(Boolean.TRUE.toString())
                .username(username)
                .credentials(List.of(
                        new CredencialUserKeycloak("password",
                                true,
                                keycloakProperties.defaultPassword())))
                .build();
    }
}