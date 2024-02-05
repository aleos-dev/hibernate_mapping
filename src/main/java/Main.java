import dao.DaoFactory;
import dto.*;
import entity.Language;
import entity.enums.Rating;
import entity.enums.SpecialFeature;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CustomerServiceImpl;
import service.FilmServiceImpl;
import service.StoreServiceImpl;
import util.DataGenerator;
import util.HibernateUtil;

import java.util.Set;

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

    private static void rentFilmByCustomerFromStore(long filmId, long customerId, long storeId) {

        var storeService = new StoreServiceImpl();

        var rentalInfo = createRentalDTO(customerId, filmId, storeId);

        storeService.rentFilm(rentalInfo);
    }





    private static void registerCustomer(String email) {

        var customerService = new CustomerServiceImpl();
        var customerDTO = CustomerDTO.builder()
                .firstName("Aleos")
                .lastName("Empty")
                .email(email)
                .addressId(1)
                .storeId(1)
                .isActive(true)
                .build();

        customerService.register(customerDTO);

    }



    private static RentalDTO createRentalDTO(long customerId, long filmId, long storeId) {

        return RentalDTO.builder()
                .fimlId(filmId)
                .storeId(storeId)
                .customerID(customerId)
                .build();
    }
}
