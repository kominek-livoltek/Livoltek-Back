package br.com.livoltek.core.internal.common.usecase.expression;

import br.com.livoltek.core.api.common.usecase.expression.GerarPredicadoDate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.PathBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GerarPredicadoDateDefault implements GerarPredicadoDate {

    @Override
    public BooleanExpression executar(String key, String operator, String value, PathBuilder<?> entityPath) {
        DatePath<LocalDate> path = entityPath.getDate(key, LocalDate.class);
        if (value.contains(",")) {
            List<LocalDate> dateValues = Stream.of(value.split(",")).map(LocalDate::parse).collect(Collectors.toList());
            return switch (operator) {
                case "=" -> path.in(dateValues);
                case "!=" -> path.notIn(dateValues);
                case "()" -> path.between(dateValues.get(0), dateValues.get(1));
                default -> null;
            };
        } else {
            LocalDate dateValue = LocalDate.parse(value);
            return switch (operator) {
                case "=" -> path.eq(dateValue);
                case ">" -> path.gt(dateValue);
                case "<" -> path.lt(dateValue);
                case ">=" -> path.goe(dateValue);
                case "<=" -> path.loe(dateValue);
                default -> null;
            };
        }
    }
}
