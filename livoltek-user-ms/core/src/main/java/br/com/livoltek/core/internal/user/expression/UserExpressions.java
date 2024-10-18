package br.com.livoltek.core.internal.user.expression;

import br.com.livoltek.core.internal.common.entity.QUserEntity;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Objects;
import java.util.UUID;

public class UserExpressions {

    private static final QUserEntity USER = QUserEntity.userEntity;

    public static BooleanExpression all() {
        return null;
    }

    public static BooleanExpression id(Long userId) {
        return Objects.nonNull(userId) ? USER.id.eq(userId) : null;
    }

    public static BooleanExpression keycloakUserId(UUID keycloakUserId) {
        return Objects.nonNull(keycloakUserId) ? USER.keycloakId.eq(keycloakUserId) : null;
    }
}
