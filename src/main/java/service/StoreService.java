package service;

import dto.RentalDTO;
import entity.Rental;

import java.util.Optional;

public interface StoreService {

    Optional<Rental> rentFilm(RentalDTO rentalDTO);

    boolean returnFilmByCustomer(long filmId, long customerId);
}
