package dao;

import dao.interfaces.Dao;
import dto.RentalDTO;
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

    public Inventory fetchAvailableInventory(List<Inventory> inventories) {

        Set<Long> ids = inventories.stream()
                .map(Inventory::getId)
                .collect(Collectors.toSet());

        String jpql = """
                SELECT i
                FROM Rental r
                RIGHT JOIN Inventory i ON r.inventory.id = i.id
                WHERE i.id IN :ids
                AND (r.returnDate IS NOT NULL
                    OR r.id IS NULL)
                """;

        return genericDao.runInContextWithResult(em -> {
            TypedQuery<Inventory> query = em.createQuery(jpql, Inventory.class);
            query.setParameter("ids", ids);
            query.setMaxResults(1);

            return query.getSingleResult();
        });
    }

    public Rental createRental(Inventory inventory, RentalDTO rentalDTO) {

        var customer = Customer.builder()
                .id(rentalDTO.getCustomerID()).build();
        var store = inventory.getStore();
        var manager = store.getStaffManager();

        var rental = Rental.builder()
                .customer(customer)
                .store(store)
                .staffManager(manager)
                .inventory(inventory)
                .build();

       genericDao.save(rental);

       return rental;
    }
}