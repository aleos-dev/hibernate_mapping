//package service;
//
//import dao.DaoFactory;
//import dto.RentalDTO;
//import entity.Customer;
//import entity.Film;
//import entity.Store;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import util.DataGenerator;
//import util.HibernateUtil;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class StoreServiceImplIntegrationTest {
//
//    private EntityManagerFactory emf;
//    private EntityManager em;
//    private StoreServiceImpl storeService;
//
//    private Customer customer;
//    private Film film;
//    private Store store;
//    private long inventoryId;
//
//    @BeforeEach
//    public void setUp() {
//        emf = HibernateUtil.getEntityManagerFactory("inMemoryDB");
//        em = emf.createEntityManager();
//        storeService = new StoreServiceImpl();
//
//        prepareTestData();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        if (em.isOpen()) {
//            em.close();
//        }
//        if (emf.isOpen()) {
//            emf.close();
//        }
//    }
//
//    private void prepareTestData() {
//
//        em.getTransaction().begin();
//
//        customer = DaoFactory.buildCustomerDao(em).fetchRandomCustomer();
//        film = DaoFactory.buildFilmDao(em).fetchRandomFilm();
//        store = DaoFactory.buildStoreDao(em).fetchRandomStore();
//
//        inventoryId = registerNewInventory(film.getId(), store.getId());
//
//        em.getTransaction().commit();
//    }
//
//    @Test
//    void rentFilm_WhenInventoryAvailable_ShouldSucceed() {
//
//        RentalDTO rentalDTO = prepareRentalDTO(customer, film.getId(), store.getId());
//
//        var rentalOptional = storeService.rentFilm(rentalDTO);
//
//        assertAll("Ensure all conditions for a successful rental",
//                () -> assertTrue(rentalOptional.isPresent(), "Film rental should succeed when inventory is available"),
//                () -> rentalOptional.ifPresent(rental -> {
//
//                    assertNotNull(rental.getCustomer(), "The rental should have an associated customer");
//                    assertEquals(customer.getId(), rental.getCustomer().getId(), "The rental's customer should match the one in the DTO");
//
//                    assertNotNull(rental.getInventory(), "The rental should have an associated film");
//                    assertEquals(inventoryId, rental.getInventory().getId(), "The rental's film should match the one in the DTO");
//
//                    assertNotNull(rental.getStore(), "The rental should have an associated store");
//                    assertEquals(store.getId(), rental.getStore().getId(), "The rental's store should match the one in the DTO");
//
//                    assertNull(rental.getReturnDate(), "The return data should be null, for new rent");
//                    assertNotNull(rental.getRentalDate(), "The rental data must not be null, for any new rental record");
//
//                    assertEquals(rental.getStaffManager().getId(), store.getStaffManager().getId(), "The manager should be the same person");
//                })
//        );
//    }
//
//    @Test
//    void rentFilm_WhenInventoryNotAvailable_ShouldFail() {
//
//        RentalDTO rentalDTO = prepareRentalDTO(customer, film.getId(), store.getId());
//        storeService.rentFilm(rentalDTO);
//
//        // the second try
//        var rentalOptional = storeService.rentFilm(rentalDTO);
//
//        assertTrue(rentalOptional.isEmpty(), "Already rented inventory must not be available for rent");
//    }
//
//    private RentalDTO prepareRentalDTO(Customer customer, long filmId, long storeId) {
//        RentalDTO rentalDTO = new RentalDTO();
//        rentalDTO.setCustomerID(customer.getId());
//        rentalDTO.setFilmId(filmId);
//        rentalDTO.setStoreId(storeId);
//        return rentalDTO;
//    }
//
//    private long registerNewInventory(long filmId, long storeId) {
//        return DaoFactory.buildInventoryDao(em).registerNewInventory(filmId, storeId);
//    }
//}