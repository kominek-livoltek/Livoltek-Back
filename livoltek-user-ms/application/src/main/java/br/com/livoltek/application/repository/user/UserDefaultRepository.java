package br.com.livoltek.application.repository.user;

import br.com.livoltek.application.repository.DefaultRepository;
import br.com.livoltek.core.api.user.repository.UserRepository;
import br.com.livoltek.core.internal.common.entity.QUserEntity;
import br.com.livoltek.core.internal.common.entity.UserEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserDefaultRepository extends DefaultRepository<UserEntity, Long> implements UserRepository {

    public UserDefaultRepository(EntityManager entityManager) {
        super(UserEntity.class, entityManager, QUserEntity.userEntity);
    }
}
