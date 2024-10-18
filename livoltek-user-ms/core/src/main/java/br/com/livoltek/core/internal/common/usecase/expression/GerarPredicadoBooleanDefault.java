package br.com.livoltek.core.internal.common.usecase.expression;

import br.com.livoltek.core.api.common.usecase.expression.GerarPredicadoBoolean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;

public class GerarPredicadoBooleanDefault implements GerarPredicadoBoolean {

    @Override
    public BooleanExpression executar(String key, String operator, String value, PathBuilder<?> entityPath) {
        if ("=".equals(operator)) {
            return entityPath.getBoolean(key).eq(Boolean.parseBoolean(value));
        } else {
            throw new RuntimeException("Unsupported Boolean operation");
        }
    }
}