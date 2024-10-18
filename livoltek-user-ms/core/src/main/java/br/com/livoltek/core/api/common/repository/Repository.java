package br.com.livoltek.core.api.common.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    T saveOne(T entity);

    List<T> saveAll(List<T> entities);

    long deleteAll(Predicate filter);

    void delete(T entity);

    List<T> searchAll(Predicate filter);

    List<T> searchAll(Predicate filter, Integer pageSize, Integer pageNumber);

    List<T> searchAll(Predicate filterPredicate, Integer pageSize, Integer pageNumber, String orderBy, String direction);

    List<T> searchAll(Predicate filter, Integer pageSize, Integer pageNumber, OrderSpecifier<?> order);

    Optional<T> search(Predicate filter);

    Long count(Predicate filter);

}
