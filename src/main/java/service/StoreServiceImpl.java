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
    public boolean rentFilm(RentalDTO rentalDTO) {

        return HibernateUtil.runInContextWithResult(em -> {

            var rentalDao = DaoFactory.buildRentalDao(em);
            var paymentDao = DaoFactory.buildPaymentDao(em);

            var customer = createCustomerFromRentalDTO(rentalDTO);
            var inventory = getInventory(em, rentalDTO);

            if (inventory.isEmpty()) return inventoryDoesNotExist(inventory, rentalDTO);

            var newRental = createRentalRecord(rentalDao, inventory, customer);

            // should be boolean....
            paymentDao.acceptPaymentForRental(newRental);

            return true;
        });
    }

    private static Rental createRentalRecord(RentalDao rentalDao, Optional<Inventory> inventory, Customer customer) {
        return rentalDao.addRental(inventory.get(), customer);
    }

    private static boolean inventoryDoesNotExist(Optional<Inventory> inventory, RentalDTO rentalDTO) {

        if (inventory.isEmpty()) {
            System.out.println(String.format("No such filmId: %s, is current available for storeId: %s", rentalDTO.getFimlId(), rentalDTO.getStoreId()));
            return true;
        }

        return false;
    }

    private static Customer createCustomerFromRentalDTO(RentalDTO rentalDTO) {
        return Customer.builder().id(rentalDTO.getCustomerID()).build();
    }

    private static Optional<Inventory> getInventory(EntityManager em, RentalDTO rentalDTO) {

        var filmId = rentalDTO.getFimlId();
        var storeId = rentalDTO.getStoreId();

        List<Inventory> inventories = DaoFactory.buildInventoryDao(em)
                .findInventoriesByFilmIdAndStoreId(filmId, storeId);

        return DaoFactory.buildRentalDao(em).fetchAvailableInventoryForRental(inventories);
    }
}