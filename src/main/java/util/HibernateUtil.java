package util;

import exception.DaoOperationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.nonNull;


public class HibernateUtil {

    private static final String defaultPU = "personalInMemory";

    private static EntityManagerFactory EMF = buildEM(defaultPU);

    private static EntityManagerFactory buildEM(String persistenceUnitName) {
        try {
            var persistenceUnit = nonNull(persistenceUnitName) ? persistenceUnitName : "personal";
            return Persistence.createEntityManagerFactory("personal");
        } catch (Exception ex) {
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void initNewEMF(String name) {
        EMF = buildEM(name);
    }

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return EMF;
    }

    public static void shutdown() {
        getEntityManagerFactory().close();
    }


    public static <T> T runInContextWithResult(Function<EntityManager, T> func) {
        var em = getEntityManager();

        try {
            em.getTransaction().begin();
            T result = func.apply(em);
            em.getTransaction().commit();

            return result;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DaoOperationException("Cannot perform DAO operation for function in context: ", e);
        } finally {
            em.close();
        }
    }

    public static void runInContext(Consumer<EntityManager> consumer) {

        var em = getEntityManager();

        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DaoOperationException("Cannot perform DAO operation for consumer in context: ", e);
        } finally {
            em.close();
        }
    }
}
