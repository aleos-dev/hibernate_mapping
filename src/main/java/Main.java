import dao.DaoFactory;
import dto.CustomerDTO;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CustomerServiceImpl;
import service.FilmServiceImpl;
import service.StoreServiceImpl;
import util.DataGenerator;
import util.HibernateUtil;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final EntityManagerFactory EMF = HibernateUtil.getEntityManagerFactory("default");

    public static void main(String[] args) {

        try {

            var customerService = new CustomerServiceImpl();
            var filmService = new FilmServiceImpl();
            var storeService = new StoreServiceImpl();

            logger.info("==============================================================START==================================");

            CustomerDTO customerDTO = DataGenerator.generateCustomerDTO();

            System.out.println(customerDTO);

            long filmId = filmService.register(DataGenerator.generateFilmDTO());

            System.out.println(DaoFactory.buildFilmDao(HibernateUtil.getEntityManager()).findById(filmId));

//            registerCustomer("newCustomerEmail@dog.com");
//            registerFilm();

//            logger.info("==============================================================RENT FILM==================================");


//            storeService.rentFilm(
//                    RentalDTO.builder()
//                            .customerID(1)
//                            .storeId(1)
//                            .fimlId(3)
//                            .build()
//            );


        } finally {
            HibernateUtil.shutdown();
        }
    }
}
