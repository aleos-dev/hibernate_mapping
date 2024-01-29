package dao;

import dao.interfaces.Dao;
import entity.StaffManager;

import java.util.List;
import java.util.Optional;

public class StaffManagerDao implements Dao<StaffManager, Long> {

    private final GenericDao<StaffManager, Long> genericDao;

    public StaffManagerDao(GenericDao<StaffManager, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<StaffManager> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<StaffManager> findAll() {
        return genericDao.findAll();
    }


    @Override
    public void save(StaffManager entity) {
        genericDao.save(entity);
    }

    @Override
    public StaffManager update(StaffManager entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(StaffManager entity) {
        genericDao.delete(entity);
    }
}