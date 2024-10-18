package br.com.livoltek.core.internal.keycloak.usecase;

import br.com.livoltek.core.api.common.service.HttpService;
import br.com.livoltek.core.api.keycloak.dto.SearchKeycloakUserResponse;
import br.com.livoltek.core.api.keycloak.usecase.GetKeycloakToken;
import br.com.livoltek.core.api.keycloak.usecase.SearchKeycloakUser;
import br.com.livoltek.core.api.properties.KeycloakProperties;
import br.com.livoltek.core.internal.common.dto.HttpRequest;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class SearchKeycloakUserDefault implements SearchKeycloakUser {

    private final KeycloakProperties keycloakProperties;
    private final GetKeycloakToken getKeycloakToken;
    private final HttpService httpService;

    @Override
    public Optional<UUID> execute(String email) {
        String bearerToken = getKeycloakToken.execute();
        String url = String.format(keycloakProperties.urlEndPointUsers()+"?email=%s", email);
        try {
            HttpRequest<Map<String, String>, SearchKeycloakUserResponse[]> request =
                    new HttpRequest<>(url, null, bearerToken);
            SearchKeycloakUserResponse[] response = httpService.get(request,
                    SearchKeycloakUserResponse[].class).getResponse();
            if(Arrays.asList(response).isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(UUID.fromString(
                    Arrays.stream(response)
                            .findFirst()
                            .get()
                            .existantUserId()));
        } catch (Exception e) {
            throw e;
        }
    }
}
