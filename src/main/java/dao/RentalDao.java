package dao;

import dao.interfaces.Dao;
import entity.Rental;

import java.util.List;
import java.util.Optional;

public class RentalDao implements Dao<Rental, Long> {

    private final GenericDao<Rental, Long> genericDao;

    public RentalDao(GenericDao<Rental, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<Rental> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<Rental> findAll() {
        return genericDao.findAll();
    }


    @Override
    public void save(Rental entity) {
        genericDao.save(entity);
    }

    @Override
    public Rental update(Rental entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(Rental entity) {
        genericDao.delete(entity);
    }
}