package service;

import dto.CustomerDTO;
import entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;


import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceImplIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        HibernateUtil.initNewEMF("personalInMemory");
        emf = HibernateUtil.getEntityManagerFactory();
        em = emf.createEntityManager();
        customerService = new CustomerServiceImpl();
    }

    @AfterEach
    public void tearDown() {
        if (em.isOpen()) {
            em.close();
        }
        if (emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    public void testRegisterValidCustomer() {
        em.getTransaction().begin();

        CustomerDTO customerDTO = new CustomerDTO();
        // Set properties on customerDTO...
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setEmail("john@example.com");
        customerDTO.setAddressId(1);
        customerDTO.setStoreId(1);
        customerDTO.setActive(true);

        assertDoesNotThrow(() -> customerService.register(customerDTO));

        em.getTransaction().commit();

        Customer foundCustomer = em.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                .setParameter("email", "john@example.com")
                .getSingleResult();

        assertNotNull(foundCustomer);
        assertEquals("John", foundCustomer.getFirstName());
        assertEquals("Doe", foundCustomer.getLastName());
    }
}
