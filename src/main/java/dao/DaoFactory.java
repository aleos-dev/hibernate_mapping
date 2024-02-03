package dao;

import entity.*;
import jakarta.persistence.EntityManager;

public class DaoFactory {

    public static ActorDao buildActorDao(EntityManager em) {
        return new ActorDao(new GenericDao<>(em, Actor.class));
    }

    public static AddressDao buildAddressDao(EntityManager em) {
        return new AddressDao(new GenericDao<>(em, Address.class));
    }

    public static CategoryDao buildCategoryDao(EntityManager em) {
        return new CategoryDao(new GenericDao<>(em, Category.class));
    }

    public static CityDao buildCityDao(EntityManager em) {
        return new CityDao(new GenericDao<>(em, City.class));
    }

    public static CountryDao buildCountryDao(EntityManager em) {
        return new CountryDao(new GenericDao<>(em, Country.class));
    }

    public static CustomerDao buildCustomerDao(EntityManager em) {
        return new CustomerDao(new GenericDao<>(em, Customer.class));
    }

    public static FilmDao buildFilmDao(EntityManager em) {
        return new FilmDao(new GenericDao<>(em, Film.class));
    }

    public static InventoryDao buildInventoryDao(EntityManager em) {
        return new InventoryDao(new GenericDao<>(em, Inventory.class));
    }

    public static LanguageDao buildLanguageDao(EntityManager em) {
        return new LanguageDao(new GenericDao<>(em, Language.class));
    }

    public static PaymentDao buildPaymentDao(EntityManager em) {
        return new PaymentDao(new GenericDao<>(em, Payment.class));
    }

    public static RentalDao buildRentalDao(EntityManager em) {
        return new RentalDao(new GenericDao<>(em, Rental.class));
    }

    public static StaffManagerDao buildStaffManagerDao(EntityManager em) {
        return new StaffManagerDao(new GenericDao<>(em, StaffManager.class));
    }

    public static StoreDao buildStoreDao(EntityManager em) {
        return new StoreDao(new GenericDao<>(em, Store.class));
    }
}
