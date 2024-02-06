package dao;

import dao.interfaces.Dao;
import entity.Store;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StoreDao implements Dao<Store, Long> {

    private final GenericDao<Store, Long> genericDao;

    public StoreDao(GenericDao<Store, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<Store> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<Store> findAll() {
        return genericDao.findAll();
    }

    @Override
    public List<Store> findByIds(Set<Long> ids) {
        return genericDao.findByIds(ids);
    }

    @Override
    public void save(Store entity) {
        genericDao.save(entity);
    }

    @Override
    public Store update(Store entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(Store entity) {
        genericDao.delete(entity);
    }

    public List<Long> registeredStoreIds() {
        return genericDao.registeredIds();
    }

    public Store fetchRandomStore() {
        return genericDao.applyFunc(em -> {
            @SuppressWarnings("unchecked")
            var query = (TypedQuery<Store>) em.createNativeQuery("SELECT * FROM store ORDER BY RANDOM() LIMIT 1", Store.class);
            return query.getSingleResult();
        });
    }

}
