package dao;

import dao.interfaces.Dao;
import entity.Category;
import entity.Customer;
import entity.Film;
import exception.FilmDTOException;
import util.HibernateUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class FilmDao implements Dao<Film, Long> {

    private final GenericDao<Film, Long> genericDao;

    public FilmDao(GenericDao<Film, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<Film> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<Film> findByIds(Set<Long> ids) {
        return genericDao.findByIds(ids);
    }

    @Override
    public List<Film> findAll() {
        return genericDao.findAll();
    }

    @Override
    public void save(Film entity) {
        genericDao.save(entity);
    }

    @Override
    public Film update(Film entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(Film entity) {
        genericDao.delete(entity);
    }

    public long registeredFilmCount() {
        return genericDao.registeredCount();
    }
}
