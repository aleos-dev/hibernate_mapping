package service;

import dao.DaoFactory;
import dto.RentalDTO;
import entity.Film;
import entity.Inventory;
import entity.Rental;
import entity.Store;
import exception.DaoOperationException;
import util.HibernateUtil;

import java.util.List;

public class StoreServiceImpl implements StoreService {
    @Override
    public boolean proceedRental(RentalDTO rentalDTO) {

        return HibernateUtil.runInContextWithResult(em -> {

            var filmId = rentalDTO.getFimlId();
            var storeId = rentalDTO.getStoreId();
            List<Inventory> inventoryIds = DaoFactory.buildInventoryDao(em)
                    .findInventoriesByFilmIdAndStoreId(filmId, storeId);

            var rentalDao = DaoFactory.buildRentalDao(em);
            var inventory = rentalDao.fetchAvailableInventory(inventoryIds);
            var createdRental = rentalDao.createRental(inventory, rentalDTO);

            DaoFactory.buildPaymentDao(em).acceptPaymentForRental(createdRental);

            return true;
        });
    }

}