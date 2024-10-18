package br.com.livoltek.core.api.user.usecase;

import br.com.livoltek.core.api.user.dto.UserResponse;

public interface CreateUser {
    UserResponse execute(String firstName, String lastName, String email, Boolean emailVerified, String username);
}
