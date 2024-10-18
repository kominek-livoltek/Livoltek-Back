package br.com.livoltek.application.repository;

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
    public long deleteAll(Predicate filter) {
        return new JPADeleteClause(entityManager, entityPath)
                .where(filter)
                .execute();
    }

    @Override
    public List<T> searchAll(Predicate filter) {
        return new JPAQueryFactory(entityManager)
                .selectFrom(entityPath)
                .where(filter)
                .fetch();
    }

    @Override
    public List<T> searchAll(Predicate filter, Integer pageSize, Integer pageNumber) {
        JPAQuery<T> query = new JPAQueryFactory(entityManager)
                .selectFrom(entityPath)
                .where(filter);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.unsorted());
        return querydsl.applyPagination(pageable, query).fetch();
    }

    @Override
    public List<T> searchAll(Predicate filter, Integer pageSize, Integer pageNumber, String orderBy, String direction) {
        JPAQuery<T> query = new JPAQueryFactory(entityManager)
                .selectFrom(entityPath)
                .where(filter);
        Sort order = Sort.by(Sort.Direction.fromString(direction), orderBy);
        Pageable paginacao = PageRequest.of(pageNumber - 1, pageSize, order);
        return querydsl.applyPagination(paginacao, query).fetch();
    }

    @Override
    public List<T> searchAll(Predicate filter, Integer pageSize, Integer pageNumber, OrderSpecifier<?> order) {
        JPAQuery<T> query = new JPAQueryFactory(entityManager)
                .selectFrom(entityPath)
                .where(filter)
                .orderBy(order);
        Pageable paginacao = PageRequest.of(pageNumber - 1, pageSize);
        return querydsl.applyPagination(paginacao, query).fetch();
    }

    @Override
    public Optional<T> search(Predicate filter) {
        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectFrom(entityPath)
                .where(filter)
                .fetchOne());
    }

    public Long count(Predicate filter) {
        return new JPAQueryFactory(entityManager)
                .select(entityPath.count())
                .from(entityPath)
                .where(filter)
                .fetchOne();
    }
}