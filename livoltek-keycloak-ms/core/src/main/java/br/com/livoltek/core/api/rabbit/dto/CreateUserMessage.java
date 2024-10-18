package br.com.livoltek.core.api.rabbit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateUserMessage {
    private String firstName;
    private String lastName;
    private String email;
    private Boolean emailVerified;
    private String username;
    private UUID keycloakUserId;
}
