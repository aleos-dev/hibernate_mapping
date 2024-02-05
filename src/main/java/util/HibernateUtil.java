package util;

import exception.DaoOperationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.nonNull;


public class HibernateUtil {

    private static final Object LOCK = new Object();
    private static EntityManagerFactory EMF;

    public static EntityManagerFactory getEntityManagerFactory(String persistenceUnitName) {
        if (EMF == null) {
            synchronized (LOCK) {
                if (EMF == null) {
                    EMF = buildEntityManagerFactory(persistenceUnitName);
                }
            }
        }
        return EMF;
    }

    private static EntityManagerFactory buildEntityManagerFactory(String persistenceUnitName) {
        try {
            String unitName = (persistenceUnitName != null && !persistenceUnitName.trim().isEmpty()) ? persistenceUnitName : "default";
            return Persistence.createEntityManagerFactory(unitName);
        } catch (Exception ex) {
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    public static void shutdown() {
        EMF.close();
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