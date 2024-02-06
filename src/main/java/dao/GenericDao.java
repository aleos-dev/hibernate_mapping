package dao;

import dao.interfaces.Dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class GenericDao<T, ID extends Serializable> implements Dao<T, ID> {

    private final Class<T> clazz;
    private final EntityManager em;

    public GenericDao(EntityManager em, Class<T> clazz) {
        this.clazz = clazz;
        this.em = em;
    }

    @Override
    public Optional<T> findById(ID id) {

        return Optional.ofNullable(em.find(clazz, id));
    }

    @Override
    public List<T> findByIds(Set<ID> ids) {

        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e.id IN :ids";


        TypedQuery<T> query = em.createQuery(jpql, clazz);
        query.setParameter("ids", ids);

        return query.getResultList();
    }

    @Override
    public List<T> findAll() {
        return em.createQuery("FROM " + clazz.getName(), clazz).getResultList();
    }

    @Override
    public void save(T entity) {
        em.persist(entity);
    }

    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    @Override
    public void delete(T entity) {
        em.remove(em.merge(entity));
    }

    public List<Long> registeredIds() {
        return em.createQuery("SELECT id FROM " + clazz.getName(), Long.class).getResultList();
    }


    public <R> R applyFunc(Function<EntityManager, R> func) {
        return func.apply(em);
    }

    public void acceptConsumer(Consumer<EntityManager> consumer) {
        consumer.accept(em);
    }
}
