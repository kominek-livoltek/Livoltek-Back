package br.com.livoltek.core.internal.common.usecase.expression;

import br.com.livoltek.core.api.common.usecase.expression.GerarPredicadoFloat;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;

import java.util.stream.Stream;

public class GerarPredicadoFloatDefault implements GerarPredicadoFloat {

    @Override
    public BooleanExpression executar(String key, String operator, String value, PathBuilder<?> entityPath) {
        NumberPath<Float> path = entityPath.getNumber(key, Float.class);
        if (value.contains(",")) {
            Float[] valueArray = Stream.of(value.split(",")).map(Float::valueOf).toArray(Float[]::new);
            return switch (operator) {
                case "=" -> path.in(valueArray);
                case "!=" -> path.notIn(valueArray);
                case "()" -> path.between(valueArray[0], valueArray[1]);
                default -> null;
            };
        }
        Float numValue = Float.valueOf(value);
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
