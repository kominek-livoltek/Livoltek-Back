package br.com.livoltek.core.internal.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@NoArgsConstructor
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
    @SequenceGenerator(name = "user_id", sequenceName = "user_id_seq", allocationSize = 1)
    Long id;

    @Column(nullable = false)
    String firstName;

    @Column
    String lastName;

    @Column
    String email;

    @Column
    UUID keycloakId;
}
