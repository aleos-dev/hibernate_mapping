package service;

import dao.DaoFactory;
import dto.FilmDTO;
import entity.Film;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import util.DataGenerator;
import util.HibernateUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FilmServiceImplIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private FilmServiceImpl filmService;

    @BeforeEach
    public void setUp() {
        emf = HibernateUtil.getEntityManagerFactory("inMemoryDB");
        em = emf.createEntityManager();
        filmService = new FilmServiceImpl();
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
    void testRegisterFilm() {
        FilmDTO newFilm = DataGenerator.generateFilmDTO();

        long filmId = filmService.register(newFilm);

        Optional<Film> persistedFilm = DaoFactory.buildFilmDao(em).findById(filmId);

        assertTrue(persistedFilm.isPresent(), "The film should be persisted");
        assertEquals(newFilm.getTitle(), persistedFilm.get().getTitle(), "The titles should match");
    }
}
