package dao;

import dao.interfaces.Dao;
import entity.Country;

import java.util.List;
import java.util.Optional;

public class CountryDao implements Dao<Country, Long> {

    private GenericDao<Country, Long> genericDao;

    public CountryDao(GenericDao<Country, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<Country> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<Country> findAll() {
        return genericDao.findAll();
    }

    @Override
    public void save(Country entity) {
        genericDao.save(entity);
    }

    @Override
    public Country update(Country entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(Country entity) {
        genericDao.delete(entity);
    }
}
