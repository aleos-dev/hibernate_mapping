import dao.DaoFactory;
import dao.InventoryDao;
import dto.*;
import entity.*;
import entity.enums.Rating;
import entity.enums.SpecialFeature;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CustomerServiceImpl;
import service.FilmServiceImpl;
import service.StoreServiceImpl;
import util.DataGenerator;
import util.HibernateUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try {

            logger.info("==============================================================START==================================");
            logger.info(HibernateUtil.runInContextWithResult(em -> em.find(Country.class, 1l).toString()));

            CustomerDTO customerDTO = DataGenerator.generateCustomerDTO();

            System.out.println(customerDTO);

//            registerCustomer("newCustomerEmail@dog.com");
//            registerFilm();

//            logger.info("==============================================================RENT FILM==================================");
            rentFilmByCustomerFromStore(3, 1, 1);


        } finally {
            HibernateUtil.shutdown();
        }
    }


    private static void rentFilmByCustomerFromStore(long filmId, long customerId, long storeId) {

        var storeService = new StoreServiceImpl();

        var rentalInfo = createRentalDTO(customerId, filmId, storeId);

        storeService.proceedRental(rentalInfo);
    }


    private static void registerFilm() {

        var filmDTO = createFilmDTO();
        var filmService = new FilmServiceImpl();

        filmService.register(filmDTO);
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

    private static FilmDTO createFilmDTO() {

        return FilmDTO.builder()
                .title("THE HARD TREE 4")
                .language(Language.builder().id(1).build())
                .originalLanguage(null)
                .description("BLOCKBASTER")
                .length(99)
                .actors(Set.of(
                        ActorDTO.builder().id(1).build(),
                        ActorDTO.builder().id(2).build())
                )
                .categories(Set.of(
                        CategoryDTO.builder().id(1).build(),
                        CategoryDTO.builder().id(2).build())
                )
                .rating(Rating.NC_17)
                .features(Set.of(
                        SpecialFeature.BEHIND_THE_SCENES,
                        SpecialFeature.COMMENTARIES
                ))
                .build();
    }


    private static RentalDTO createRentalDTO(long customerId, long filmId, long storeId) {

        return RentalDTO.builder()
                .fimlId(filmId)
                .storeId(storeId)
                .customerID(customerId)
                .build();
    }
}
