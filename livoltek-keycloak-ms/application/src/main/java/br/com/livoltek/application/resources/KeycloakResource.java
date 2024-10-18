package br.com.livoltek.application.resources;

import br.com.livoltek.core.api.keycloak.dto.CreateUserKeycloakRequest;
import br.com.livoltek.core.api.keycloak.usecase.CreateUserKeycloak;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("keycloak")
@RequiredArgsConstructor
public class KeycloakResource {

    private final CreateUserKeycloak createUserKeycloak;

    @PostMapping()
    UUID createUserKeycloak(@RequestBody CreateUserKeycloakRequest createUserKeycloakRequest) {
        return createUserKeycloak.execute(createUserKeycloakRequest.firstName(),
                                          createUserKeycloakRequest.lastName(),
                                          createUserKeycloakRequest.email(),
                                          createUserKeycloakRequest.emailVerified(),
                                          createUserKeycloakRequest.username());
    }
}