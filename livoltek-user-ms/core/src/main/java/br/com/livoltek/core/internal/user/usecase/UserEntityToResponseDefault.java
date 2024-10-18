package br.com.livoltek.core.internal.user.usecase;

import br.com.livoltek.core.api.user.dto.UserResponse;
import br.com.livoltek.core.api.user.usecase.UserEntityToResponse;
import br.com.livoltek.core.internal.common.entity.UserEntity;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

public class UserEntityToResponseDefault implements UserEntityToResponse {

    @Override
    public UserResponse execute(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .keycloakId(userEntity.getKeycloakId())
                .build();
    }
}
