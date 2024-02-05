package util;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.BaseProducer;
import com.devskiller.jfairy.producer.person.Person;
import com.devskiller.jfairy.producer.text.TextProducer;
import dao.DaoFactory;
import dto.*;
import entity.Category;
import entity.Language;
import entity.enums.Rating;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataGenerator {

    private static List<Long> storeIds;
    private static List<Long> addressIds;
    private static List<Long> filmIds;
    private static List<Long> languageIds;
    private static List<Long> categoryIds;
    private static List<Long> actorIds;

    private static Fairy fairy = Fairy.create();
    private static Person fairyPerson = fairy.person();
    private static BaseProducer fairyBaseProducer = fairy.baseProducer();
    private static TextProducer textProducer = fairy.textProducer();

    static {
        refreshDataCount();
    }

    public static List<CustomerDTO> generateCustomerList(int size) {
        return Stream.generate(DataGenerator::generateCustomerDTO)
                .limit(size)
                .collect(Collectors.toList());
    }

    public static CustomerDTO generateCustomerDTO() {

        CustomerDTO fakeCustomer = new CustomerDTO();
        fakeCustomer.setFirstName(fairyPerson.getFirstName());
        fakeCustomer.setLastName(fairyPerson.getLastName());
        fakeCustomer.setEmail(fairyPerson.getEmail());

        fakeCustomer.setStoreId(fairyBaseProducer.randomElement(storeIds));
        fakeCustomer.setAddressId(fairyBaseProducer.randomElement(addressIds));
        fakeCustomer.setActive(true);

        return fakeCustomer;
    }

    public static FilmDTO generateFilmDTO() {

        FilmDTO fakeFilm = new FilmDTO();
        fakeFilm.setTitle(textProducer.latinWord());
        fakeFilm.setLanguage(generateLanguageDTO());
        fakeFilm.setDescription(textProducer.latinSentence());
        fakeFilm.setLength(fairyBaseProducer.randomBetween(70, 150));
        fakeFilm.setCategories(generateSetOfCategoryDTO());
        fakeFilm.setActors(generateSetOfActorDTO());

        var rating = Rating.values();
        fakeFilm.setRating(rating[fairyBaseProducer.randomBetween(0, rating.length)]);

        return fakeFilm;
    }

    public static Set<CategoryDTO> generateSetOfCategoryDTO() {

        return Stream.generate(() ->
                CategoryDTO.builder().id(fairyBaseProducer.randomElement(categoryIds)).build())
                .limit(fairyBaseProducer.randomBetween(1,categoryIds.size()))
                .collect(Collectors.toSet());
    }

    public static Set<ActorDTO> generateSetOfActorDTO() {

        return Stream.generate(() ->
                        ActorDTO.builder().id(fairyBaseProducer.randomElement(actorIds)).build())
                .limit(fairyBaseProducer.randomBetween(2, actorIds.size()))
                .collect(Collectors.toSet());
    }


    public static LanguageDTO generateLanguageDTO() {

        return LanguageDTO.builder().id(fairyBaseProducer.randomElement(languageIds)).build();
    }

    public static void refreshDataCount() {

        HibernateUtil.runInContext(em -> {
            storeIds = DaoFactory.buildStoreDao(em).registeredStoreIds();
            addressIds = DaoFactory.buildAddressDao(em).registeredAddressIds();
            filmIds = DaoFactory.buildFilmDao(em).registeredFilmIds();
            languageIds = DaoFactory.buildLanguageDao(em).registeredLanguageIds();
            categoryIds = DaoFactory.buildCategoryDao(em).registeredCategoryIds();
            actorIds = DaoFactory.buildActorDao(em).registeredActorIds();
        });
    }
}
