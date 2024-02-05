package service;

import dao.DaoFactory;
import dao.RentalDao;
import dto.RentalDTO;
import entity.Customer;
import entity.Inventory;
import entity.Rental;
import exception.StoreServiceException;
import jakarta.persistence.EntityManager;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class StoreServiceImpl implements StoreService {

    @Override
    public Optional<Rental> rentFilm(RentalDTO rentalDTO) {

        return HibernateUtil.runInContextWithResult(em -> {

            var rentalDao = DaoFactory.buildRentalDao(em);
            var paymentDao = DaoFactory.buildPaymentDao(em);

            var customer = createCustomerFromRentalDTO(rentalDTO);
            var inventory = getInventory(em, rentalDTO);

            if (inventory.isEmpty()) {
                inventoryDoesNotExist(inventory, rentalDTO);
                return Optional.empty();
            }

            Rental newRental = rentalDao.addRental(inventory.get(), customer);
            paymentDao.acceptPaymentForRental(newRental);

            return Optional.of(newRental);
        });
    }


    private boolean inventoryDoesNotExist(Optional<Inventory> inventory, RentalDTO rentalDTO) {

        if (inventory.isEmpty()) {
            System.out.println(String.format("No such filmId: %s, is current available for storeId: %s", rentalDTO.getFilmId(), rentalDTO.getStoreId()));
            return true;
        }

        return false;
    }

    private Customer createCustomerFromRentalDTO(RentalDTO rentalDTO) {
        return Customer.builder().id(rentalDTO.getCustomerID()).build();
    }

    private Optional<Inventory> getInventory(EntityManager em, RentalDTO rentalDTO) {

        var filmId = rentalDTO.getFilmId();
        var storeId = rentalDTO.getStoreId();

        List<Inventory> inventories = DaoFactory.buildInventoryDao(em)
                .findInventoriesByFilmIdAndStoreId(filmId, storeId);

        return DaoFactory.buildRentalDao(em).fetchAvailableInventoryForRental(inventories);
    }
}