package service;

import dao.DaoFactory;
import dto.FilmDTO.FilmDTO;
import entity.Actor;
import entity.Category;
import entity.Film;
import entity.Language;
import exception.FilmDTOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import util.HibernateUtil;

import java.util.*;
import java.util.stream.Collectors;

public class FilmServiceImpl implements FilmService {

    @Override
    public void register(FilmDTO newFilm) {

        HibernateUtil.runInContext(em -> {

                    var lang = findLang(newFilm.getLanguage(), em);
                    var originalLang = findLang(newFilm.getOriginalLanguage(), em);
                    var features = Film.convertSpecialFeaturesToString(newFilm.getFeatures());
                    var actors = findActors(newFilm, em);
                    var categories = findCategoriesAndFetchFilmsCollection(newFilm, em);

                    Film createdFilm = Film.builder()
                            .title(newFilm.getTitle())
                            .language(lang)
                            .originalLanguage(originalLang)
                            .description(newFilm.getDescription())
                            .length(newFilm.getLength())
                            .rating(newFilm.getRating())
                            .actors(actors)
                            .specialFeatures(features)
                            .build();

                    categories.forEach(createdFilm::addCategory);

                    DaoFactory.buildFilmDao(em).save(createdFilm);


//                    DaoFactory.buildFilmDao(em).save(createdFilm);
                }
        );
    }

    private static Set<Actor> findActors(FilmDTO newFilm, EntityManager em) {

        if (Objects.nonNull(newFilm.getActors())) {


            var actorDao = DaoFactory.buildActorDao(em);



            return newFilm.getActors().stream()
                    .map(Actor::getId)
                    .map(actorDao::findById)
                    .map(a -> a.orElseThrow(() -> new FilmDTOException("Invalid Actor ID of DTO doesn't exist")))
                    .collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }

    private static List<Category> findCategoriesAndFetchFilmsCollection(FilmDTO newFilm, EntityManager em) {

        if (Objects.nonNull(newFilm.getCategories())) {

            List<Long> categoryIds = newFilm.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());

            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c left join fetch c.films WHERE c.id IN :ids", Category.class);
            query.setParameter("ids", categoryIds);

            List<Category> fetchedCategories = query.getResultList();

            if (fetchedCategories.size() != categoryIds.size()) {
                throw new FilmDTOException("One or more Category IDs from DTO do not exist");
            }

            return fetchedCategories;
        }

        return Collections.emptyList();
    }

    private static Language findLang(Language lang, EntityManager em) {

        var languageDao = DaoFactory.buildLanguageDao(em);

        if (Objects.isNull(lang)) return null;

        return languageDao.findById(lang.getId())
                .orElseThrow(() -> new FilmDTOException("Language id must be existed in db"));
    }
}
