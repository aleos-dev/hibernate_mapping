package service;

import dao.DaoFactory;
import dto.CustomerDTO;
import entity.Customer;
import exception.CustomerDTOException;
import jakarta.persistence.EntityManagerFactory;
import util.HibernateUtil;

public class CustomerServiceImpl implements CustomerService {

    @Override
    public void register(CustomerDTO customerDTO) {

        HibernateUtil.runInContext(em -> {
            var address = DaoFactory.buildAddressDao(em).findById(customerDTO.getAddressId());
            var store = DaoFactory.buildStoreDao(em).findById(customerDTO.getStoreId());

            if (customerDTO.getId() != 0) throw new CustomerDTOException("Customer to be saved must have no id");
            if (address.isEmpty() || store.isEmpty()) throw new CustomerDTOException("Such store/address don't exists in db");

            var newCustomer = Customer.builder()
                    .firstName(customerDTO.getFirstName())
                    .lastName(customerDTO.getLastName())
                    .email(customerDTO.getEmail())
                    .address(address.get())
                    .store(store.get())
                    .isActive(customerDTO.isActive())
                    .build();


            DaoFactory.buildCustomerDao(em)
                    .save(newCustomer);
        });
    }
}
