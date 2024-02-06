package service;

import dto.CustomerDTO;
import entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DataGenerator;
import util.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceImplIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        emf = HibernateUtil.initEMF("inMemoryDB");
        em = emf.createEntityManager();
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

        CustomerDTO customerDTO = DataGenerator.generateCustomerDTO();

        assertDoesNotThrow(() -> new CustomerServiceImpl().register(customerDTO));

        em.getTransaction().commit();

        Customer foundCustomer = em.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                .setParameter("email", customerDTO.getEmail())
                .getSingleResult();

        assertNotNull(foundCustomer);
        assertEquals(customerDTO.getFirstName(), foundCustomer.getFirstName());
        assertEquals(customerDTO.getLastName(), foundCustomer.getLastName());
    }
}
