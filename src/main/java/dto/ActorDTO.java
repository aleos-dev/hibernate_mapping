package dto;

import entity.Film;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ActorDTO {

    private long id;
    private String firstName;
    private String lastName;
    private Set<Film> films;
}
