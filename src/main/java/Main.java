import dao.DaoFactory;
import dao.LanguageDao;
import dto.CustomerDTO.CustomerDTO;
import dto.FilmDTO.FilmDTO;
import entity.*;
import entity.enums.Rating;
import entity.enums.SpecialFeature;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import service.CustomerServiceImpl;
import service.FilmServiceImpl;
import util.HibernateUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {

    private static final EntityManagerFactory EMF = HibernateUtil.getEntityManagerFactory();
    private static final String SCHEMA_NAME = "mapping_personal_db";

    public static void main(String[] args) {

        try {
//            initAll();

            registerCustomer(null);
            registerFilm();


        } finally {
            HibernateUtil.shutdown();
        }
    }

    private static void registerFilm() {

        var filmDTO = createFilmDTO();
        var filmService = new FilmServiceImpl();

        filmService.register(filmDTO);
    }

    private static FilmDTO createFilmDTO() {

        var categoryDao = DaoFactory.buildCategoryDao();
        var actorDao = DaoFactory.buildActorDao();

        return FilmDTO.builder()
                .title("THE HARD TREE 4")
                .language(Language.builder().id(1).build())
                .originalLanguage(null)
                .description("BLOCKBASTER")
                .length(99)
                .categories(Set.of(
                        categoryDao.findById(1L).get(),
                        categoryDao.findById(2L).get())
                )
                .actors(Set.of(
                        actorDao.findById(1L).get(),
                        actorDao.findById(2L).get()
                ))
                .rating(Rating.NC_17)
                .features(Set.of(
                        SpecialFeature.BEHIND_THE_SCENES,
                        SpecialFeature.COMMENTARIES
                ))
                .build();

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


    private static void initAll() {

        initCountries();
        initLanguage();
        initCities();
        initFilms();
        initAddress();
        initStore();
        initCategories();
        initActors();
    }

    private static void initActors() {

        runConsumerInSession(em -> {
            Query nativeQuery = em.createNativeQuery("""
                            INSERT INTO mapping_personal_db.actor (first_name,last_name) VALUES
                            ('PENELOPE','GUINESS'),
                            ('NICK','WAHLBERG'),
                            ('ED','CHASE'),
                            ('JENNIFER','DAVIS'),
                            ('JOHNNY','LOLLOBRIGIDA'),
                            ('BETTE','NICHOLSON'),
                            ('GRACE','MOSTEL'),
                            ('MATTHEW','JOHANSSON'),
                            ('JOE','SWANK'),
                            ('CHRISTIAN','GABLE');
                    """);
            nativeQuery.executeUpdate();
        });
    }


    private static void initCategories() {
        runConsumerInSession(em -> {
            Query nativeQuery = em.createNativeQuery("""
                    INSERT INTO mapping_personal_db.category (name) VALUES
                            ('Action'),
                            ('Animation'),
                            ('Children'),
                            ('Classics'),
                            ('Comedy'),
                            ('Documentary'),
                            ('Drama'),
                            ('Family'),
                            ('Foreign'),
                            ('Games'),
                            ('Horror'),
                            ('Music'),
                            ('New'),
                            ('Sci-Fi'),
                            ('Sports'),
                            ('Travel');
                    """);

            nativeQuery.executeUpdate();
        });
    }

    private static void initAddress() {

        runConsumerInSession(em -> {
            var city = em.find(City.class, 1L);
            var city2 = em.find(City.class, 2L);

            var address = Address.builder()
                    .address("23 Workhaven Lane")
                    .districtName("Alberta")
                    .city(city)
                    .phone("14033335568")
                    .build();

            var address2 = Address.builder()
                    .address("1411 Lillydale Drive")
                    .districtName("QLD")
                    .city(city2)
                    .phone("14033335568")
                    .build();

            em.persist(address);
            em.persist(address2);
        });
    }

    private static void initStore() {
        runConsumerInSession(em -> {
            var address = em.find(Address.class, 1);
//            var manager = em.find(StaffManager.class, 1);

            var store = Store.builder()
                    .address(address)
                    .build();

            em.persist(store);
        });
    }


    private static void initFilms() {

        runConsumerInSession(em -> {
            Language lang = em.find(Language.class, 1);


            var film = Film.builder()
                    .title("ACADEMY DINOSAUR")
                    .description("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies")
                    .releaseYear(2007)
                    .language(lang)
                    .rentalInfo(new Film.RentalInfo(4, BigDecimal.valueOf(4.21), BigDecimal.valueOf(25.11)))
                    .length(86)
                    .build();

            var film2 = Film.builder()
                    .title("ACADEMY DINOSAUR 2")
                    .description("A second part")
                    .releaseYear(2008)
                    .language(lang)
                    .length(86)
                    .build();

            em.persist(film);
            em.persist(film2);
        });
    }

    private static void initCities() {

        City darkCity = City.builder().name("Dark city").country(runFuncInSession(em -> em.find(Country.class, 1))).build();
        City greenCity = City.builder().name("Green city").country(runFuncInSession(em -> em.find(Country.class, 2))).build();

        runConsumerInSession(em -> {

            var empire = em.find(Country.class, 2);
            empire.addCity(darkCity);
            empire.addCity(greenCity);
            em.persist(empire);
        });
    }

    private static void initLanguage(Language... languages) {

        if (languages.length == 0) {
            var lang = Language.builder().name("English").build();

            runConsumerInSession(em -> em.persist(lang));
            return;
        }

        Arrays.stream(languages)
                .forEach(c -> runConsumerInSession(em -> em.persist(c)));

    }

    private static void initCountries(Country... countries) {


        if (countries.length == 0) {
            runConsumerInSession(em -> {
                Query nativeQuery = em.createNativeQuery("CREATE SEQUENCE " + SCHEMA_NAME + ".country_id_seq START 1 INCREMENT BY 1");
                nativeQuery.executeUpdate();
                var country1 = new Country().builder().name("Tree Kingdom").build();
                var country2 = new Country().builder().name("Red Empire").build();
                var country3 = new Country().builder().name("Aleosland").build();

                em.persist(country1);
                em.persist(country2);
                em.persist(country3);
            });
        }

        Arrays.stream(countries)
                .forEach(c -> runConsumerInSession(em -> em.persist(c)));
    }


    private static <T> T runFuncInSession(Function<EntityManager, T> emFunc) {

        var em = EMF.createEntityManager();

        try {

            em.getTransaction().begin();
            T result = emFunc.apply(em);
            em.getTransaction().commit();

            return result;
        } catch (Exception e) {
            System.out.println("Can't perform func in session");
            throw e;
        } finally {
            em.close();
        }
    }

    private static void runConsumerInSession(Consumer<EntityManager> emConsumer) {

        var em = EMF.createEntityManager();

        try {

            em.getTransaction().begin();
            emConsumer.accept(em);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Can't perform func in session");
            throw e;
        } finally {
            em.close();
        }
    }
}
