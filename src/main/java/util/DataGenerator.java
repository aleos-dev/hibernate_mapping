package util;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import dao.DaoFactory;
import dto.CustomerDTO;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataGenerator {

    private static long storeCount;
    private static long addressCount;
    private static long filmCount;

    static {
        refreshDataCount();
    }

    public static List<CustomerDTO> generateCustomerList(int size) {
        return Stream.generate(DataGenerator::generateCustomerDTO)
                .limit(size)
                .collect(Collectors.toList());
    }

    public static CustomerDTO generateCustomerDTO() {
        Fairy fairy = Fairy.create();
        Person customer = fairy.person();
        Random random = new Random();

        CustomerDTO fakeCustomer = new CustomerDTO();
        fakeCustomer.setFirstName(customer.getFirstName());
        fakeCustomer.setLastName(customer.getLastName());
        fakeCustomer.setEmail(customer.getEmail());

        fakeCustomer.setStoreId(random.nextLong(1, storeCount));
        fakeCustomer.setAddressId(random.nextLong(1, addressCount));
        fakeCustomer.setActive(true);

        return fakeCustomer;
    }

    public static void refreshDataCount() {

        HibernateUtil.runInContext(em -> {
            storeCount = DaoFactory.buildStoreDao(em).registeredStoreCount();
            addressCount = DaoFactory.buildAddressDao(em).registeredAddressCount();
            filmCount = DaoFactory.buildFilmDao(em).registeredFilmCount();
        });
    }
}
