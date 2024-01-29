package dao;

import dao.interfaces.Dao;
import entity.Inventory;

import java.util.List;
import java.util.Optional;

public class InventoryDao implements Dao<Inventory, Long> {

    private final GenericDao<Inventory, Long> genericDao;

    public InventoryDao(GenericDao<Inventory, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<Inventory> findAll() {
        return genericDao.findAll();
    }


    @Override
    public void save(Inventory entity) {
        genericDao.save(entity);
    }

    @Override
    public Inventory update(Inventory entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(Inventory entity) {
        genericDao.delete(entity);
    }
}
