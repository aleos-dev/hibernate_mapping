package dao;

import dao.interfaces.Dao;
import entity.*;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public List<Rental> findByIds(Set<Long> ids) {
        return genericDao.findByIds(ids);
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

    public Optional<Inventory> fetchAvailableInventoryForRental(List<Inventory> inventories) {

        Set<Long> ids = inventories.stream()
                .map(Inventory::getId)
                .collect(Collectors.toSet());

        String jpql = """
                SELECT i
                FROM Rental r
                RIGHT JOIN Inventory i ON r.inventory.id = i.id
                JOIN FETCH i.store
                JOIN FETCH i.store.staffManager
                WHERE i.id IN :ids
                AND (r.returnDate IS NOT NULL
                    OR r.id IS NULL)
                """;

        return genericDao.applyFunc(em -> {
            TypedQuery<Inventory> query = em.createQuery(jpql, Inventory.class);
            query.setParameter("ids", ids);
            query.setMaxResults(1);

            List<Inventory> resultList = query.getResultList();
            return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
        });
    }

    public Rental addRental(Inventory inventory, Customer customer) {

        var store = inventory.getStore();
        var manager = store.getStaffManager();

        Rental rental = Rental.builder()
                .customer(customer)
                .store(store)
                .staffManager(manager)
                .inventory(inventory)
                .build();

       genericDao.save(rental);

       return rental;
    }
}