package dao;

import dao.interfaces.Dao;
import entity.Store;

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
}
