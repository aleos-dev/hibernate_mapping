package dao;

import dao.interfaces.Dao;
import jakarta.persistence.EntityManager;
import util.HibernateUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GenericDao<T, ID extends Serializable> implements Dao<T, ID> {

    private final Class<T> clazz;
    private final EntityManager em;

    protected GenericDao(Class<T> clazz) {
        this.clazz = clazz;
        em = null;
    }

    public GenericDao(EntityManager em, Class<T> clazz) {
        this.clazz = clazz;
        this.em = em;
    }

    @Override
    public Optional<T> findById(ID id) {

        return Objects.nonNull(em)
                ? Optional.ofNullable(em.find(clazz, id))
                : Optional.ofNullable(HibernateUtil.runInContextWithResult(em -> em.find(clazz, id)));
    }

    @Override
    public List<T> findAll() {

        return Objects.nonNull(em)
                ? em.createQuery("FROM " + clazz.getName(), clazz).getResultList()
                : HibernateUtil.runInContextWithResult(em -> em.createQuery("FROM " + clazz.getName(), clazz).getResultList());
    }

    @Override
    public void save(T entity) {

        if (Objects.nonNull(em)) {

            em.persist(entity);

        } else HibernateUtil.runInContext(em -> em.persist(entity));
    }

    @Override
    public T update(T entity) {
        return Objects.nonNull(em) ? em.merge(entity) : HibernateUtil.runInContextWithResult(em -> em.merge(entity));
    }

    @Override
    public void delete(T entity) {

        if (Objects.nonNull(em)) {

            em.remove(em.merge(entity));

        } else HibernateUtil.runInContext(
                em -> {
                    var merged = em.merge(entity);
                    em.remove(merged);
                });
    }
}
