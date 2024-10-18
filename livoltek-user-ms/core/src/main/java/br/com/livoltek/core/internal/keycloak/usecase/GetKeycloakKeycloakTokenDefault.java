package br.com.livoltek.core.internal.keycloak.usecase;

import br.com.livoltek.core.api.common.service.HttpService;
import br.com.livoltek.core.api.keycloak.dto.AuthTokenResponse;
import br.com.livoltek.core.api.keycloak.usecase.GetKeycloakToken;
import br.com.livoltek.core.api.properties.KeycloakProperties;
import br.com.livoltek.core.internal.common.dto.HttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class GetKeycloakKeycloakTokenDefault implements GetKeycloakToken {

    private final KeycloakProperties keycloakProperties;
    private final HttpService httpService;
    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";

    @Override
    public String execute() {
        return generateToken();
    }

    private String generateToken() {

        String grantType = keycloakProperties.grantType();
        String clientId = keycloakProperties.clientId();
        String clientSecret = keycloakProperties.clientSecret();
        String url = keycloakProperties.urlTokenClientSecret();

        Map<String, String> payload = generateRequest(grantType, clientId, clientSecret);

        try {
            HttpRequest<Map<String, String>, AuthTokenResponse> request
                    = new HttpRequest<>(url, payload);
            request =  httpService.postUrlEncoded(request, AuthTokenResponse.class);
            AuthTokenResponse response = request.getResponse();
            return Objects.requireNonNull(response.accessToken(), "Empty token");
        } catch (Exception e) {
            throw new RuntimeException("Error generating token in the authentication service");
        }
    }

    private Map<String, String> generateRequest(String grantType, String clientId, String clientSecret) {
        Map<String, String> mapRequest = new HashMap<>();
        mapRequest.put(GRANT_TYPE, grantType);
        mapRequest.put(CLIENT_ID, clientId);
        mapRequest.put(CLIENT_SECRET, clientSecret);
        return mapRequest;
    }
}
