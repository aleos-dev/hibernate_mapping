package dao;

import dao.interfaces.Dao;
import entity.Actor;
import entity.Address;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AddressDao implements Dao<Address, Long> {

    private final GenericDao<Address, Long> genericDao;

    public AddressDao(GenericDao<Address, Long> genericDao) {
        this.genericDao = genericDao;
    }


    @Override
    public List<Address> findByIds(Set<Long> ids) {
        return genericDao.findByIds(ids);
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
