package dao;

import dao.interfaces.Dao;
import entity.Address;

import java.util.List;
import java.util.Optional;

public class AddressDao implements Dao<Address, Long> {

    private final GenericDao<Address, Long> genericDao;

    public AddressDao(GenericDao<Address, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<Address> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<Address> findAll() {
        return genericDao.findAll();
    }


    @Override
    public void save(Address entity) {
        genericDao.save(entity);
    }

    @Override
    public Address update(Address entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(Address entity) {
        genericDao.delete(entity);
    }
}
