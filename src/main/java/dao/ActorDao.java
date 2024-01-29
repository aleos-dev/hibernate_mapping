package dao;

import dao.interfaces.Dao;
import entity.Actor;

import java.util.List;
import java.util.Optional;

public class ActorDao implements Dao<Actor, Long> {

    private final GenericDao<Actor, Long> genericDao;

    public ActorDao(GenericDao<Actor, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<Actor> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<Actor> findAll() {
        return genericDao.findAll();
    }


    @Override
    public void save(Actor entity) {
        genericDao.save(entity);
    }

    @Override
    public Actor update(Actor entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(Actor entity) {
        genericDao.delete(entity);
    }
}
