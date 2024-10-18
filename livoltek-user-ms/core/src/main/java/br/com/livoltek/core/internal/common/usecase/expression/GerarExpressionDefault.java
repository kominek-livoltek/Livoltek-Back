package br.com.livoltek.core.internal.common.usecase.expression;

import br.com.livoltek.core.api.common.usecase.expression.*;
import br.com.livoltek.core.internal.common.dto.Filter;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class GerarExpressionDefault implements GerarExpression {

    private final GerarPredicadoInteger gerarPredicadoInteger;
    private final GerarPredicadoLong gerarPredicadoLong;
    private final GerarPredicadoDecimal gerarPredicadoDecimal;
    private final GerarPredicadoFloat gerarPredicadoFloat;
    private final GerarPredicadoBoolean gerarPredicadoBoolean;
    private final GerarPredicadoString gerarPredicadoString;
    private final GerarPredicadoDate gerarPredicadoDate;
    private final GerarPredicadoDateTime gerarPredicateDateTime;

    @Override
    public BooleanExpression executar(List<String> filtrosString, Class<?> type) {
        List<Filter> filters = filtrosString.stream()
                .map(this::gerarFiltro)
                .toList();
        BooleanExpression booleanExpression = Expressions.asBoolean(true).isTrue();
        if (filters.isEmpty()) return booleanExpression;
        String entityVariable = getEntityVariable(type.getSimpleName());
        PathBuilder<?> entityPath = new PathBuilder<>(type, entityVariable);
        List<BooleanExpression> expressions = filters.stream()
                .map(filter -> getPredicate(filter.getKey(), filter.getOperator(),
                        filter.getValue(), entityPath, type))
                .toList();
        for (BooleanExpression exp : expressions) {
            booleanExpression = booleanExpression.and(exp);
        }
        return booleanExpression;
    }

    private Filter gerarFiltro(String filtro) {
        final Pattern pattern = Pattern
                .compile("([\\w.]+?)(=|>|<|>=|<=|!=|:|!:|%|\\+|\\(\\))([\\wA-zÀ-ú\\s\\(\\)@,.:-]+?)\\|");
        Matcher m = pattern.matcher(filtro + "|");
        if (m.find()) {
            return new Filter(m.group(1), m.group(2), m.group(3));
        } else {
            throw new RuntimeException("Formato de filtro inválido");
        }
    }

    private static String getEntityVariable(String simpleClassName) {
        char[] c = simpleClassName.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    private BooleanExpression getPredicate(String key, String operator, String value, PathBuilder<?> entityPath,
                                           Class<?> classType) {
        Class<?> propertyType = getPropertyType(classType, key);
        return switch (propertyType.getSimpleName()) {
            case "Integer" -> gerarPredicadoInteger.executar(key, operator, value, entityPath);
            case "Long" -> gerarPredicadoLong.executar(key, operator, value, entityPath);
            case "BigDecimal" -> gerarPredicadoDecimal.executar(key, operator, value, entityPath);
            case "Float" -> gerarPredicadoFloat.executar(key, operator, value, entityPath);
            case "Boolean" -> gerarPredicadoBoolean.executar(key, operator, value, entityPath);
            case "String" -> gerarPredicadoString.executar(key, operator, value, entityPath);
            case "LocalDate" -> gerarPredicadoDate.executar(key, operator, value, entityPath);
            case "LocalDateTime" -> gerarPredicateDateTime.executar(key, operator, value, entityPath);
            default -> gerarPredicadoString.executar(key, operator, value, entityPath);
        };
    }

    private Class<?> getPropertyType(Class<?> parent, String property) {
        List<String> propertyList = new LinkedList<>(Arrays.asList(property.split("\\.")));
        return getRecursiveType(parent, propertyList);
    }

    @SneakyThrows(NoSuchFieldException.class)
    private Class<?> getRecursiveType(Class<?> parent, List<String> propertyList) {
        if (propertyList.size() > 1) {
            Field field = parent.getDeclaredField(propertyList.get(0));
            Class<?> child = field.getType();
            propertyList.remove(propertyList.get(0));
            if ("Set".equals(child.getSimpleName())) {
                return child;
                //ParameterizedType type = (ParameterizedType) field.getGenericType();
                //return getRecursiveType((Class<?>) type.getActualTypeArguments()[0], propertyList);
            }
            return getRecursiveType(child, propertyList);
        } else {
            return parent.getDeclaredField(propertyList.get(0)).getType();
        }
    }
}