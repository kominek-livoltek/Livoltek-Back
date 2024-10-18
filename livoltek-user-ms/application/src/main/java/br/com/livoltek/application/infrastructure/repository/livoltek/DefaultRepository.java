package br.com.livoltek.application.infrastructure.repository.livoltek;

import br.com.livoltek.core.api.common.repository.Repository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class DefaultRepository<T, ID> extends SimpleJpaRepository<T, ID> implements Repository<T> {

    protected final EntityManager entityManager;
    protected final EntityPathBase<T> entityPath;
    private final Querydsl querydsl;

    public DefaultRepository(Class<T> domainClass, EntityManager entityManager, EntityPathBase<T> entityPath) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.entityPath = entityPath;
        this.querydsl = new Querydsl(entityManager, new PathBuilderFactory().create(domainClass));
    }

    @Override
    @Transactional
    public T saveOne(T entity) {
        return super.save(entity);
    }

    @Override
    @Transactional
    public List<T> saveAll(List<T> entities) {
        return super.saveAll(entities);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        super.delete(entity);
    }

    @Override
    @Transactional
    public long deleteAll(Predicate filtro) {
        return new JPADeleteClause(entityManager, entityPath)
                .where(filtro)
                .execute();
    }

    @Override
    public List<T> searchAll(Predicate filtro) {
        return new JPAQueryFactory(entityManager)
                .selectFrom(entityPath)
                .where(filtro)
                .fetch();
    }

    @Override
    public List<T> searchAll(Predicate filtro, Integer tamanhoPagina, Integer numeroPagina) {
        JPAQuery<T> query = new JPAQueryFactory(entityManager)
                .selectFrom(entityPath)
                .where(filtro);
        Pageable pageable = PageRequest.of(numeroPagina - 1, tamanhoPagina, Sort.unsorted());
        return querydsl.applyPagination(pageable, query).fetch();
    }

    @Override
    public List<T> searchAll(Predicate filtro, Integer tamanhoPagina, Integer numeroPagina, String ordenadoPor, String direcao) {
        JPAQuery<T> query = new JPAQueryFactory(entityManager)
                .selectFrom(entityPath)
                .where(filtro);
        Sort ordenacao = Sort.by(Sort.Direction.fromString(direcao), ordenadoPor);
        Pageable paginacao = PageRequest.of(numeroPagina - 1, tamanhoPagina, ordenacao);
        return querydsl.applyPagination(paginacao, query).fetch();
    }

    @Override
    public List<T> searchAll(Predicate filtro, Integer tamanhoPagina, Integer numeroPagina, OrderSpecifier<?> ordenacao) {
        JPAQuery<T> query = new JPAQueryFactory(entityManager)
                .selectFrom(entityPath)
                .where(filtro)
                .orderBy(ordenacao);
        Pageable paginacao = PageRequest.of(numeroPagina - 1, tamanhoPagina);
        return querydsl.applyPagination(paginacao, query).fetch();
    }

    @Override
    public Optional<T> search(Predicate filtro) {
        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectFrom(entityPath)
                .where(filtro)
                .fetchOne());
    }

    public Long count(Predicate filtro) {
        return new JPAQueryFactory(entityManager)
                .select(entityPath.count())
                .from(entityPath)
                .where(filtro)
                .fetchOne();
    }
}