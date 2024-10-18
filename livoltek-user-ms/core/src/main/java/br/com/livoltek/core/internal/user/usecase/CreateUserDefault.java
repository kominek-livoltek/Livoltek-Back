package br.com.livoltek.core.internal.user.usecase;

import br.com.livoltek.core.api.keycloak.usecase.CreateKeycloakUser;
import br.com.livoltek.core.api.keycloak.usecase.SearchKeycloakUser;
import br.com.livoltek.core.api.user.dto.UserResponse;
import br.com.livoltek.core.api.user.repository.UserRepository;
import br.com.livoltek.core.api.user.usecase.CreateUser;
import br.com.livoltek.core.api.user.usecase.UserEntityToResponse;
import br.com.livoltek.core.internal.common.entity.UserEntity;
import br.com.livoltek.core.internal.user.expression.UserExpressions;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreateUserDefault implements CreateUser {

    private final UserRepository userRepository;
    private final SearchKeycloakUser searchKeycloakUser;
    private final CreateKeycloakUser createKeycloakUser;
    private final UserEntityToResponse userEntityToResponse;

    @Override
    public UserResponse execute(String firstName, String lastName, String email,
                                 Boolean emailVerified, String username) {
        Optional<UUID> keycloakUser = searchKeycloakUser.execute(email);
        if (keycloakUser.isPresent())
            throw new RuntimeException("User already exists");
        UserEntity temporaryUser = createUser(firstName, lastName, email);
        UUID keycloakUserId =  createKeycloakUser.execute(firstName, lastName, email, emailVerified, username);
        UserEntity user = updateUserKeycloakId(temporaryUser.getId(), keycloakUserId)
                .orElseThrow(() -> new RuntimeException("Failed to update user identifier"));
        return userEntityToResponse.execute(user);
    }

    public UserEntity createUser(String firstName, String lastName, String email) {
        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return userRepository.saveOne(user);
    }

    public Optional<UserEntity> updateUserKeycloakId(Long userId, UUID keycloakUserId) {
        Optional<UserEntity> userOptional = userRepository.search(UserExpressions.id(userId));
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (Objects.isNull(user.getKeycloakId())) {
                user.setKeycloakId(keycloakUserId);
                userRepository.saveOne(user);
            }
        }
        return userOptional;
    }
}