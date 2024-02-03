package dto;

import entity.Language;
import entity.enums.Rating;
import entity.enums.SpecialFeature;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "id")
@Builder
public class FilmDTO {

    private long id;
    private String title;
    private Language language;
    private Language originalLanguage;
    private String description;
    private Integer length;
    private Set<CategoryDTO> categories;
    private Set<ActorDTO> actors;
    private Rating rating;
    private Set<SpecialFeature> features;
}
