package br.com.livoltek.core.internal.common.usecase.expression;

import br.com.livoltek.core.api.common.usecase.expression.GerarPredicadoLong;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;

import java.util.stream.Stream;

public class GerarPredicadoLongDefault implements GerarPredicadoLong {

    @Override
    public BooleanExpression executar(String key, String operator, String value, PathBuilder<?> entityPath) {
        NumberPath<Long> path = entityPath.getNumber(key, Long.class);
        if (value.contains(",")) {
            Long[] valueArray = Stream.of(value.split(",")).map(Long::parseLong).toArray(Long[]::new);
            return switch (operator) {
                case "=" -> path.in(valueArray);
                case "!=" -> path.notIn(valueArray);
                case "()" -> path.between(valueArray[0], valueArray[1]);
                default -> null;
            };
        }
        Long numValue = Long.parseLong(value);
        return switch (operator) {
            case "=" -> path.eq(numValue);
            case ">" -> path.gt(numValue);
            case "<" -> path.lt(numValue);
            case ">=" -> path.goe(numValue);
            case "<=" -> path.loe(numValue);
            case "!=" -> path.ne(numValue);
            default -> null;
        };
    }
}
