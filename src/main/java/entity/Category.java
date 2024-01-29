package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category", uniqueConstraints = @UniqueConstraint(name = "uq_category_name", columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Category name cannot be blank")
    @Column(nullable = false, length = 25)
    private String name;

    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "film_category", foreignKey = @ForeignKey(name = "fk_film_category"),
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id"))
    private Set<Film> films = new HashSet<>();


    public void addFilm(Film film) {
        films.add(film);
        film.getCategories().add(this);
    }

    public void removeFilm(Film film) {
        films.remove(film);
        film.getCategories().remove(this);
    }

    @UpdateTimestamp
    @Column(name = "last_update", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private ZonedDateTime lastUpdate;
}
