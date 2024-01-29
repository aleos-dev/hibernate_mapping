package dao;

import dao.interfaces.Dao;
import entity.Language;

import java.util.List;
import java.util.Optional;

public class LanguageDao implements Dao<Language, Long> {

    private final GenericDao<Language, Long> genericDao;

    public LanguageDao(GenericDao<Language, Long> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public Optional<Language> findById(Long id) {
        return genericDao.findById(id);
    }

    @Override
    public List<Language> findAll() {
        return genericDao.findAll();
    }


    @Override
    public void save(Language entity) {
        genericDao.save(entity);
    }

    @Override
    public Language update(Language entity) {
        return genericDao.update(entity);
    }

    @Override
    public void delete(Language entity) {
        genericDao.delete(entity);
    }
}