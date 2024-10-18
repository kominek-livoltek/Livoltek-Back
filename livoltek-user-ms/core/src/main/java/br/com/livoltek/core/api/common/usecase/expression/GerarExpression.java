package br.com.livoltek.core.api.common.usecase.expression;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

public interface GerarExpression {

    BooleanExpression executar(List<String> filtrosString, Class<?> type);
}
