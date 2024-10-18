package br.com.livoltek.core.internal.common.usecase.expression;

import br.com.livoltek.core.api.common.usecase.expression.GerarPredicadoString;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GerarPredicadoStringDefault implements GerarPredicadoString {

    @Override
    public BooleanExpression executar(String key, String operator, String value, PathBuilder<?> entityPath) {
        StringPath path = entityPath.getString(key);
        if (value.contains(",")) {
            List<String> values = Stream.of(value.split(",")).collect(Collectors.toList());
            return switch (operator) {
                case "=" -> path.in(values);
                case "!=" -> path.notIn(values);
                default -> null;
            };
        }
        return switch (operator) {
            case "=" -> path.equalsIgnoreCase(value);
            case "!=" -> path.notEqualsIgnoreCase(value);
            case "%" -> path.startsWithIgnoreCase(value);
            case "+" -> path.containsIgnoreCase(value);
            default -> null;
        };
    }
}
