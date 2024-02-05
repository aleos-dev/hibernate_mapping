package dao;

import dao.interfaces.Dao;
import entity.Country;
import entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CustomerDao implements Dao<Customer, Long> {

    private final GenericDao<Customer, Long> genericDao;

    public CustomerDao(GenericDao<Customer, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<Customer> findByIds(Set<Long> ids) {
        return genericDao.findByIds(ids);
    }

    @Override
    public List<Customer> findAll() {
        return genericDao.findAll();
    }


    @Override
    public void save(Customer entity) {
        genericDao.save(entity);
    }

    @Override
    public Customer update(Customer entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(Customer entity) {
        genericDao.delete(entity);
    }

    public List<Long> registeredCustomerIds() {
        return genericDao.registeredIds();
    }

}
