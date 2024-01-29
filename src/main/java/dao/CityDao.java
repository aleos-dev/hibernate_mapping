package dao;

import dao.interfaces.Dao;
import entity.City;

import java.util.List;
import java.util.Optional;

public class CityDao implements Dao<City, Long> {

    private final GenericDao<City, Long> genericDao;

    public CityDao(GenericDao<City, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<City> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<City> findAll() {
        return genericDao.findAll();
    }


    @Override
    public void save(City entity) {
        genericDao.save(entity);
    }

    @Override
    public City update(City entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(City entity) {
        genericDao.delete(entity);
    }
}
