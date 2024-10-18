package br.com.livoltek.core.api.common.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T> {

    T salvar(T entity);

    List<T> salvarTodos(List<T> entities);

    long apagarTodos(Predicate filtro);

    void apagar(T entity);

    List<T> buscarTodos(Predicate filtro);

    List<T> buscarTodos(Predicate filtro, Integer tamanhoPagina, Integer numeroPagina);

    List<T> buscarTodos(Predicate filtroPredicate, Integer tamanhoPagina, Integer numeroPagina, String ordenadoPor, String direcao);

    List<T> buscarTodos(Predicate filtro, Integer tamanhoPagina, Integer numeroPagina, OrderSpecifier<?> ordenacao);

    Optional<T> buscar(Predicate filtro);

    Long contar(Predicate filtro);

}
