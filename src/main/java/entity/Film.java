package entity;

import entity.converter.RatingConverter;
import entity.enums.Rating;
import entity.enums.SpecialFeature;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "film", indexes = {
        @Index(name = "index_film_language", columnList = "language_id"),
        @Index(name = "index_film_title", columnList = "title"),
        @Index(name = "index_film_original_language", columnList = "original_language_id")})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 128, message = "Title must not exceed 128 characters")
    @Column(nullable = false, length = 128)
    private String title;

    private String description;

    private Integer length;

    @Column(name = "release_year")
    private Integer releaseYear;

    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id", nullable = false, foreignKey = @ForeignKey(name = "fk_film_language"))
    private Language language;

    @ManyToOne
    @JoinColumn(name = "original_language_id", foreignKey = @ForeignKey(name = "fk_film_original_language"))
    private Language originalLanguage;

    @ToString.Exclude
    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ManyToMany
    @JoinTable(name = "actor_film", foreignKey = @ForeignKey(name = "fk_actor_film"),
            joinColumns = @JoinColumn(name = "film_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "actor_id", nullable = false))
    private Set<Actor> actors = new HashSet<>();

    @ToString.Exclude
    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "films", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Category> categories = new HashSet<>();

    @Embedded
    @Builder.Default
    private RentalInfo rentalInfo = new RentalInfo();

    @Convert(converter = RatingConverter.class)
    private Rating rating;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "special_features")
    private String specialFeatures;

    @UpdateTimestamp
    @Column(name = "last_update")
    private ZonedDateTime lastUpdate;


    public static String convertSpecialFeaturesToString(Collection<SpecialFeature> features) {

        return features == null
                ? null
                : features.stream().map(SpecialFeature::toString).collect(Collectors.joining(","));
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.getFilms().add(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.getFilms().remove(this);
    }

    public void addActor(Actor actor) {
        actors.add(actor);
        actor.getFilms().add(this);
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
        actor.getFilms().remove(this);
    }

    public void setSpecialFeatures(Collection<SpecialFeature> features) {
        specialFeatures = convertSpecialFeaturesToString(features);
    }

    public Set<SpecialFeature> getSpecialFeatures() {

        if (specialFeatures == null || specialFeatures.trim().isEmpty()) {
            return Set.of();
        }

        return Arrays.stream(specialFeatures.split(","))
                .map(SpecialFeature::parseSpecialFeature)
                .collect(Collectors.toSet());
    }

    @Embeddable
    @AttributeOverrides({
            @AttributeOverride(name = "rentalDuration", column = @Column(name = "rental_duration", nullable = false)),
            @AttributeOverride(name = "rentalRate", column = @Column(name = "rental_rate", precision = 4, scale = 2, nullable = false)),
            @AttributeOverride(name = "replacementCost", column = @Column(name = "replacement_cost", precision = 5, scale = 2, nullable = false))
    })
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class RentalInfo {

        private int rentalDuration = 3;
        private BigDecimal rentalRate = BigDecimal.valueOf(4.99);
        private BigDecimal replacementCost = BigDecimal.valueOf(19.99);
    }
}


