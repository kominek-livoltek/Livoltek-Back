package br.com.livoltek.core.internal.common.usecase.expression;

import br.com.livoltek.core.api.common.usecase.expression.GerarPredicadoDecimal;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class GerarPredicadoDecimalDefault implements GerarPredicadoDecimal {

    @Override
    public BooleanExpression executar(String key, String operator, String value, PathBuilder<?> entityPath) {
        NumberPath<BigDecimal> path = entityPath.getNumber(key, BigDecimal.class);
        if (value.contains(",")) {
            BigDecimal[] valueArray = Stream.of(value.split(",")).map(BigDecimal::new).toArray(BigDecimal[]::new);
            return switch (operator) {
                case "=" -> path.in(valueArray);
                case "!=" -> path.notIn(valueArray);
                case "()" -> path.between(valueArray[0], valueArray[1]);
                default -> null;
            };
        }
        BigDecimal numValue = new BigDecimal(value);
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
