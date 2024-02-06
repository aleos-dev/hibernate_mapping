import dao.DaoFactory;
import dto.RentalDTO;
import entity.Store;
import lombok.extern.slf4j.Slf4j;
import service.CustomerServiceImpl;
import service.FilmServiceImpl;
import service.StoreServiceImpl;
import util.DataGenerator;
import util.HibernateUtil;

@Slf4j
public class Main {

    static {
        HibernateUtil.initEMF("default");
    }


    public static void main(String[] args) {

        try {

            runBaseTestForServices();

        } finally {
            HibernateUtil.shutdown();
        }
    }

    private static void runBaseTestForServices() {
        var customerService = new CustomerServiceImpl();
        var filmService = new FilmServiceImpl();
        var storeService = new StoreServiceImpl();

        log.info("==============================================================START==================================");

        log.info("==============================================================Register Customer======================");

        var customerDTO = DataGenerator.generateCustomerDTO();
        var customerId = customerService.register(customerDTO);

        log.info("==============================================================Register Film==========================");

        var filmDTO = DataGenerator.generateFilmDTO();
        long filmId = filmService.register(filmDTO);


        log.info("==============================================================RENT FILM==============================");

        var rentalDTO = arrangeInventoryAndGetRentalDTO(filmId, customerId);
        var rentalOptional = storeService.rentFilm(rentalDTO);

        log.info("==============================================================RESULT=================================");

        //noinspection OptionalGetWithoutIsPresent
        printResult(filmId, customerId, rentalOptional.get().getId());

        log.info("==============================================================RETURN RENT============================");

        storeService.returnFilmByCustomer(filmId, customerId);


        log.info("==============================================================RESULT AFTER RETURN====================");

        printResult(filmId, customerId, rentalOptional.get().getId());
    }

    private static void printResult(long filmId, long customerId, long rentalId) {

        HibernateUtil.runInContext(em -> {
            System.out.println(DaoFactory.buildFilmDao(em).findById(filmId));
            System.out.println(DaoFactory.buildCustomerDao(em).findById(customerId));
            System.out.println(DaoFactory.buildRentalDao(em).findById(rentalId));

        });
    }

    private static RentalDTO arrangeInventoryAndGetRentalDTO(long filmId, long customerId) {

        return HibernateUtil.runInContextWithResult(em -> {

            Store store = DaoFactory.buildStoreDao(em).fetchRandomStore();

            DaoFactory.buildInventoryDao(em).registerNewInventory(filmId, store.getId());

            return RentalDTO.builder()
                    .filmId(filmId)
                    .storeId(store.getId())
                    .customerID(customerId)
                    .build();

        });
    }
}
