package dao;

import dao.interfaces.Dao;
import entity.Film;
import entity.Inventory;
import entity.Store;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public List<Inventory> findByIds(Set<Long> ids) {
        return genericDao.findByIds(ids);
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


    public List<Inventory> findInventoriesByFilmIdAndStoreId(long filmId, long storeId) {

        String jpql = """
                SELECT i
                from Inventory i
                    JOIN FETCH i.film
                    JOIN FETCH i.store
                WHERE
                    i.store.id = :storeId
                AND
                    i.film.id = :filmId
                """;

        return genericDao.applyFunc(em -> {

            var query = em.createQuery(jpql, Inventory.class);
            query.setParameter("filmId", filmId);
            query.setParameter("storeId", storeId);

            query.setHint("org.hibernate.readOnly", true);

            List<Inventory> resultList = query.getResultList();

            // todo: find out how to open session in READ_MODE
            em.clear();

            return resultList;
        });
    }

    public void registerNewInventory(long filmId, long storeId) {

        Inventory inventory = Inventory.builder()
                .film(Film.builder().id(filmId).build())
                .store(Store.builder().id(storeId).build())
                .build();

        genericDao.save(inventory);
    }
}
