package service;

import dao.DaoFactory;
import dto.RentalDTO;
import entity.Customer;
import entity.Inventory;
import entity.Rental;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import util.HibernateUtil;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class StoreServiceImpl implements StoreService {

    @Override
    public Optional<Rental> rentFilm(RentalDTO rentalDTO) {

        Objects.requireNonNull(rentalDTO);

        return HibernateUtil.runInContextWithResult(em -> {

            var rentalDao = DaoFactory.buildRentalDao(em);
            var paymentDao = DaoFactory.buildPaymentDao(em);

            var customerDTO = arrangeCustomerDTOBasedOn(rentalDTO);
            var inventory = getAvailableInventory(em, rentalDTO);

            if (inventory.isEmpty()) {
                inventoryDoesNotExist(rentalDTO);
                return Optional.empty();
            }

            Rental newRental = rentalDao.addRental(inventory.get(), customerDTO);
            paymentDao.acceptPaymentForRental(newRental);


            return Optional.of(newRental);
        });
    }

    @Override
    public boolean returnFilmByCustomer(long filmId, long customerId) {

        return HibernateUtil.runInContextWithResult(em -> {

            var query = em.createQuery("""
                            SELECT p.rental
                            FROM Payment p
                            WHERE p.rental.inventory.film.id = :filmId
                            AND p.rental.customer.id = :customerId
                            AND p.rental.returnDate IS NULL
                    """, Rental.class);

            query.setParameter("filmId", filmId);
            query.setParameter("customerId", customerId);

            List<Rental> rentalList = query.getResultList();

            if (rentalList.isEmpty()) {
                System.out.printf("Rental record not found for FilmID: %d, and CustomerID: %d%n",
                        filmId, customerId);
                return false;
            }

            var rental = rentalList.getFirst();

            rental.setReturnDate(ZonedDateTime.now());

            return true;
        });
}


private void inventoryDoesNotExist(RentalDTO rentalDTO) {

        log.info(String.format("No such filmId: %s, is current available for storeId: %s", rentalDTO.getFilmId(), rentalDTO.getStoreId()));
}

private Customer arrangeCustomerDTOBasedOn(RentalDTO rentalDTO) {
    return Customer.builder().id(rentalDTO.getCustomerID()).build();
}

private Optional<Inventory> getAvailableInventory(EntityManager em, RentalDTO rentalDTO) {

    var filmId = rentalDTO.getFilmId();
    var storeId = rentalDTO.getStoreId();

    List<Inventory> inventories = DaoFactory.buildInventoryDao(em)
            .findInventoriesByFilmIdAndStoreId(filmId, storeId);

    return DaoFactory.buildRentalDao(em).fetchAvailableInventoryForRental(inventories);
}
}