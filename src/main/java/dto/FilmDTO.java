package dto;

import entity.enums.Rating;
import entity.enums.SpecialFeature;
import lombok.*;

import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmDTO {

    private long id;
    private String title;
    private LanguageDTO language;
    private LanguageDTO originalLanguage;
    private String description;
    private Integer length;
    private Set<CategoryDTO> categories;
    private Set<ActorDTO> actors;
    private Rating rating;
    private Set<SpecialFeature> features;
}
