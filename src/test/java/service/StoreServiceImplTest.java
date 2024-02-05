package service;

import dao.DaoFactory;
import dto.RentalDTO;
import entity.Inventory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DataGenerator;
import util.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

class StoreServiceImplTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private FilmServiceImpl filmService;

    @BeforeEach
    public void setUp() {
        emf = HibernateUtil.getEntityManagerFactory("inMemoryDB");
        em = emf.createEntityManager();
        filmService = new FilmServiceImpl();
    }

    @AfterEach
    public void tearDown() {
        if (em.isOpen()) {
            em.close();
        }
        if (emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    void rentFilm_WhenInventoryAvailable_ShouldSucceed() {
        // Arrange: Ensure there is available inventory for the film in the store
        DaoFactory.buildInventoryDao(em).registerNewInventory();

        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setCustomerID(/* existing customer ID */);
        rentalDTO.setFilmId(/* existing film ID with available inventory */);
        rentalDTO.setStoreId(/* existing store ID */);

        // Act: Try to rent the film
        boolean result = storeService.rentFilm(rentalDTO);

        // Assert: Check that the film was successfully rented
        assertTrue(result, "Film rental should succeed when inventory is available");
    }

    @Test
    void rentFilm_WhenInventoryNotAvailable_ShouldFail() {
        // Arrange: Ensure there is no available inventory for the film in the store
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setCustomerID(/* existing customer ID */);
        rentalDTO.setFilmId(/* film ID with no available inventory */);
        rentalDTO.setStoreId(/* existing store ID */);

        // Act: Try to rent the film
        boolean result = storeService.rentFilm(rentalDTO);

        // Assert: Check that the film rental failed due to lack of inventory
        assertFalse(result, "Film rental should fail when no inventory is available");
    }

}