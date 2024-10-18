package br.com.livoltek.core.internal.common.usecase.expression;

import br.com.livoltek.core.api.common.usecase.expression.GerarPredicadoDateTime;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.PathBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GerarPredicadoDateTimeDefault implements GerarPredicadoDateTime {

    @Override
    public BooleanExpression executar(String key, String operator, String value, PathBuilder<?> entityPath) {
        DatePath<LocalDateTime> path = entityPath.getDate(key, LocalDateTime.class);
        if (value.contains(",")) {
            List<LocalDate> dateValues = Stream.of(value.split(",")).map(LocalDate::parse).collect(Collectors.toList());
            return switch (operator) {
                case "=" ->
                        path.in(dateValues.parallelStream().map(LocalDate::atStartOfDay).collect(Collectors.toList()));
                case "()" -> path.between(dateValues.get(0).atStartOfDay(), dateValues.get(1).atTime(LocalTime.MAX));
                default -> null;
            };
        } else {
            LocalDate dateValue = LocalDate.parse(value);
            return switch (operator) {
                case "=" -> path.between(dateValue.atStartOfDay(), dateValue.atTime(LocalTime.MAX));
                case ">" -> path.gt(dateValue.atTime(LocalTime.MAX));
                case "<" -> path.lt(dateValue.atStartOfDay());
                case ">=" -> path.goe(dateValue.atStartOfDay());
                case "<=" -> path.loe(dateValue.atTime(LocalTime.MAX));
                default -> null;
            };
        }
    }
}