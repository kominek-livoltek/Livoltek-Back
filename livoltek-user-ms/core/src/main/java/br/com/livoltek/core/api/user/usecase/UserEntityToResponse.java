package br.com.livoltek.core.api.user.usecase;

import br.com.livoltek.core.api.user.dto.UserResponse;
import br.com.livoltek.core.internal.common.entity.UserEntity;

public interface UserEntityToResponse {

    UserResponse execute(UserEntity userEntity);

}
