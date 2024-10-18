package br.com.livoltek.application.resources;

import br.com.livoltek.core.api.user.dto.CreateUserRequest;
import br.com.livoltek.core.api.user.dto.UserResponse;
import br.com.livoltek.core.api.user.usecase.CreateUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "User Management")
public class UserResource {

    private final CreateUser createUser;

    @PostMapping
    @Operation(summary = "Create a new user")
    public UserResponse createUserKeycloak(@RequestBody CreateUserRequest createUserRequest) {
        return createUser.execute(createUserRequest.firstName(),
                createUserRequest.lastName(),
                createUserRequest.email(),
                createUserRequest.emailVerified(),
                createUserRequest.username());
    }
}
