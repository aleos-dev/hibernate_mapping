package service;

import dao.DaoFactory;
import dto.ActorDTO;
import dto.CategoryDTO;
import dto.FilmDTO;
import entity.Actor;
import entity.Category;
import entity.Film;
import entity.Language;
import exception.ActorDTOException;
import exception.FilmDTOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import util.HibernateUtil;

import java.util.*;
import java.util.stream.Collectors;

public class FilmServiceImpl implements FilmService {

    @Override
    public long register(FilmDTO newFilm) {

        return HibernateUtil.runInContextWithResult(em -> {

                    var features = Film.convertSpecialFeaturesToString(newFilm.getFeatures());
                    var lang = Language.builder().id(newFilm.getLanguage().getId()).build();
                    var origLang = Objects.isNull(newFilm.getOriginalLanguage())
                            ? null
                            : Language.builder().id(newFilm.getOriginalLanguage().getId()).build();
                    var actors = newFilm.getActors().stream()
                            .map(dto -> Actor.builder().id(dto.getId()).build())
                            .collect(Collectors.toSet());

                    Film createdFilm = Film.builder()
                            .title(newFilm.getTitle())
                            .language(lang)
                            .originalLanguage(origLang)
                            .description(newFilm.getDescription())
                            .length(newFilm.getLength())
                            .rating(newFilm.getRating())
                            .actors(actors)
                            .specialFeatures(features)
                            .build();

                    // todo: just an example of owning side effect on difference between category and actor record creation
                    // todo: will be good to make insert as batch operation
                    var categories = findCategoriesAndFetchFilmsCollection(newFilm, em);
                    categories.forEach(c -> c.addFilm(createdFilm));

                    DaoFactory.buildFilmDao(em).save(createdFilm);

                    return createdFilm.getId();
                }
        );
    }

    private static Set<Category> findCategoriesAndFetchFilmsCollection(FilmDTO newFilm, EntityManager em) {

        if (Objects.nonNull(newFilm.getCategories())) {

            Set<Long> categoryIds = newFilm.getCategories().stream()
                    .map(CategoryDTO::getId)
                    .collect(Collectors.toSet());

            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c left join fetch c.films WHERE c.id IN :ids", Category.class);
            query.setParameter("ids", categoryIds);

            List<Category> fetchedCategories = query.getResultList();

            if (fetchedCategories.size() != categoryIds.size()) {
                throw new FilmDTOException("One or more Category IDs from DTO do not exist");
            }

            return new HashSet<>(fetchedCategories);
        }

        return Collections.emptySet();
    }

    private static Set<Actor> findActors(FilmDTO newFilm, EntityManager em) {

        if (Objects.nonNull(newFilm.getActors())) {

            Set<Long> actorIds = newFilm.getActors().stream()
                    .map(ActorDTO::getId)
                    .collect(Collectors.toSet());

            var actors = DaoFactory.buildActorDao(em).findByIds(actorIds);

            if (actors.size() != actorIds.size()) {
                throw new ActorDTOException("One or more Actor IDs from DTO do not exist");
            }

            return new HashSet<>(actors);
        }

        return Collections.emptySet();
    }


    private static Language findLang(Language lang, EntityManager em) {

        var languageDao = DaoFactory.buildLanguageDao(em);

        if (Objects.isNull(lang)) return null;

        return languageDao.findById(lang.getId())
                .orElseThrow(() -> new FilmDTOException("Language id must be existed in db"));
    }
}
