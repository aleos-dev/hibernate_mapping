package service;

import dao.DaoFactory;
import dto.RentalDTO;
import entity.Rental;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DataGenerator;
import util.HibernateUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StoreServiceImplIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private StoreServiceImpl storeService;

    private long customerId;
    private long filmId;
    private long storeId;
    private long inventoryId;

    @BeforeEach
    public void setUp() {
        emf = HibernateUtil.initEMF("inMemoryDB");
        em = emf.createEntityManager();
        storeService = new StoreServiceImpl();

        prepareTestData();
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

    private void prepareTestData() {

        em.getTransaction().begin();

        customerId = DaoFactory.buildCustomerDao(em).fetchRandomCustomer().getId();
        storeId = DaoFactory.buildStoreDao(em).fetchRandomStore().getId();
        filmId = new FilmServiceImpl().register(DataGenerator.generateFilmDTO());

        inventoryId = registerNewInventory(filmId, storeId);

        em.getTransaction().commit();
    }

    @Test
    void rentFilm_WhenInventoryAvailable_ShouldSucceed() {

        RentalDTO rentalDTO = prepareRentalDTO(customerId, filmId, storeId);

        var rentalOptional = storeService.rentFilm(rentalDTO);

        assertAll("Ensure all conditions for a successful rental",
                () -> assertTrue(rentalOptional.isPresent(), "Film rental should succeed when inventory is available"),
                () -> rentalOptional.ifPresent(rental -> {

                    assertNotNull(rental.getCustomer(), "The rental should have an associated customer");
                    assertEquals(customerId, rental.getCustomer().getId(), "The rental's customer should match the one in the DTO");

                    assertNotNull(rental.getInventory(), "The rental should have an associated film");
                    assertEquals(inventoryId, rental.getInventory().getId(), "The rental's film should match the one in the DTO");

                    assertNotNull(rental.getStore(), "The rental should have an associated store");
                    assertEquals(storeId, rental.getStore().getId(), "The rental's store should match the one in the DTO");

                    assertNull(rental.getReturnDate(), "The return data should be null, for new rent");
                    assertNotNull(rental.getRentalDate(), "The rental data must not be null, for any new rental record");

                    assertEquals(rental.getStaffManager().getId(), rental.getStore().getStaffManager().getId(), "The manager should be the same person");
                })
        );
    }

    @Test
    void rentFilm_WhenInventoryNotAvailable_ShouldFail() {

        RentalDTO rentalDTO = rentFilmAndReturnDTO();

        var rentalOptional = storeService.rentFilm(rentalDTO);

        assertTrue(rentalOptional.isEmpty(), "Already rented inventory must not be available for rent");
    }

    @Test
    void givenAlreadyRentedFilm_whenReturned_thenShouldUpdateRentalReturnDate() {

        RentalDTO rentalDTO = prepareRentalDTO(customerId, filmId, storeId);

        Optional<Rental> rentedFilmOptional = storeService.rentFilm(rentalDTO);
        assertTrue(rentedFilmOptional.isPresent(), "The film should be rented successfully.");

        Rental rentedFilm = rentedFilmOptional.get();

        boolean isReturnSuccess = storeService.returnFilmByCustomer(rentalDTO.getFilmId(), rentalDTO.getCustomerID());
        assertTrue(isReturnSuccess, "The already rented film should be returned successfully.");

        Rental returnedFilm = DaoFactory.buildRentalDao(em).findById(rentedFilm.getId())
                .orElseThrow(() -> new AssertionError("Rental record should exist."));
        assertNotNull(returnedFilm.getReturnDate(), "The film's return date should be updated after return.");
    }

    @Test
    void givenNonRentedFilm_whenReturned_thenShouldUpdateRentalReturnDate() {

        RentalDTO rentalDTO = prepareRentalDTO(customerId, filmId, storeId);

        boolean isReturnSuccess = storeService.returnFilmByCustomer(rentalDTO.getFilmId(), rentalDTO.getCustomerID());

        assertFalse(isReturnSuccess, "The no-rented film should be not returned.");
    }

    private RentalDTO rentFilmAndReturnDTO() {
        RentalDTO rentalDTO = prepareRentalDTO(customerId, filmId, storeId);
        storeService.rentFilm(rentalDTO);
        return rentalDTO;
    }


    private RentalDTO prepareRentalDTO(long customerId, long filmId, long storeId) {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setCustomerID(customerId);
        rentalDTO.setFilmId(filmId);
        rentalDTO.setStoreId(storeId);
        return rentalDTO;
    }

    private long registerNewInventory(long filmId, long storeId) {
        return DaoFactory.buildInventoryDao(em).registerNewInventory(filmId, storeId);
    }
}