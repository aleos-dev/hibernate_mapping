package dao;

import dao.interfaces.Dao;
import entity.Address;
import entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CategoryDao implements Dao<Category, Long> {

    private final GenericDao<Category, Long> genericDao;

    public CategoryDao(GenericDao<Category, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<Category> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<Category> findByIds(Set<Long> ids) {
        return genericDao.findByIds(ids);
    }

    @Override
    public List<Category> findAll() {
        return genericDao.findAll();
    }


    @Override
    public void save(Category entity) {
        genericDao.save(entity);
    }

    @Override
    public Category update(Category entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(Category entity) {
        genericDao.delete(entity);
    }
}
