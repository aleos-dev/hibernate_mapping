package service;

import dao.DaoFactory;
import dto.CustomerDTO.CustomerDTO;
import entity.Customer;
import exception.CustomerDTOException;

public class CustomerServiceImpl implements CustomerService {

    @Override
    public void register(CustomerDTO customerDTO) {

        var address = DaoFactory.buildAddressDao().findById(customerDTO.getAddressId());
        var store = DaoFactory.buildStoreDao().findById(customerDTO.getStoreId());

        if (customerDTO.getId() != 0) throw new CustomerDTOException("Customer to be saved must have no id");
        if (address.isEmpty() || store.isEmpty()) throw new CustomerDTOException("Such store/address don't exists in db");

        DaoFactory.buildCustomerDao()
                .save(Customer.builder()
                        .firstName(customerDTO.getFirstName())
                        .lastName(customerDTO.getLastName())
                        .email(customerDTO.getEmail())
                        .address(address.get())
                        .store(store.get())
                        .isActive(customerDTO.isActive())
                        .build()
                );
    }
}
