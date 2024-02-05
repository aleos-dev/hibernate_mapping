package service;

import dao.DaoFactory;
import dto.RentalDTO;
import entity.Customer;
import entity.Film;
import entity.Rental;
import entity.Store;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

class StoreServiceImplIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private StoreServiceImpl storeService;

    @BeforeEach
    public void setUp() {
        emf = HibernateUtil.getEntityManagerFactory("inMemoryDB");
        em = emf.createEntityManager();
        storeService = new StoreServiceImpl();
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

        em.getTransaction().begin();

        var customer = DaoFactory.buildCustomerDao(em).fetchRandomCustomer();
        var film = DaoFactory.buildFilmDao(em).fetchRandomFilm();
        var store = DaoFactory.buildStoreDao(em).fetchRandomStore();

        var inventoryID = registerNewInventory(film, store);

        em.getTransaction().commit();

        RentalDTO rentalDTO = createRentalDTO(customer, film, store);
        var rentalOptional = storeService.rentFilm(rentalDTO);


        assertTrue(rentalOptional.isPresent(), "Film rental should succeed when inventory is available");

        rentalOptional.ifPresent(rental -> {
            assertNotNull(rental.getCustomer(), "The rental should have an associated customer");
            assertEquals(customer.getId(), rental.getCustomer().getId(), "The rental's customer should match the one in the DTO");

            assertNotNull(rental.getInventory(), "The rental should have an associated film");
            assertEquals(inventoryID, rental.getInventory().getId(), "The rental's film should match the one in the DTO");

            assertNotNull(rental.getStore(), "The rental should have an associated store");
            assertEquals(store.getId(), rental.getStore().getId(), "The rental's store should match the one in the DTO");

            assertNull(rental.getReturnDate(), "The return data should be null, for new rent");
            assertNotNull(rental.getRentalDate(), "The rental data must not be null, for any new rental record");

            assertEquals(rental.getStaffManager().getId(), store.getStaffManager().getId(), "The manager should be the same person");
        });
    }

    private RentalDTO createRentalDTO(Customer customer, Film film, Store store) {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setCustomerID(customer.getId());
        rentalDTO.setFilmId(film.getId());
        rentalDTO.setStoreId(store.getId());
        return rentalDTO;
    }

    private long registerNewInventory(Film film, Store store) {
        return DaoFactory.buildInventoryDao(em).registerNewInventory(film.getId(), store.getId());
    }

    /*@Test
    void rentFilm_WhenInventoryNotAvailable_ShouldFail() {
    }*/

}