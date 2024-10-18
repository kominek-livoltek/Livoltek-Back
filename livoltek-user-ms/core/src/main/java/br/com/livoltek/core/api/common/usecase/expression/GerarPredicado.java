package br.com.livoltek.core.api.common.usecase.expression;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;

public interface GerarPredicado {

    BooleanExpression executar(String key, String operator, String value, PathBuilder<?> entityPath);
}
