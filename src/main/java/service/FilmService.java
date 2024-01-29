package service;

import dto.FilmDTO.FilmDTO;
import entity.Film;

public interface FilmService {

    void register(FilmDTO newFilm);
}
