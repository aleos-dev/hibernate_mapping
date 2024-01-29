package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "actor", indexes = @Index(name = "index_actor_last_name", columnList = "last_name"))
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Actor first name cannot be blank")
    @Size(max = 45, message = "Actor first name must not exceed 45 characters")
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @NotBlank(message = "Actor last name cannot be blank")
    @Size(max = 45, message = "Actor last name must not exceed 45 characters")
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @ToString.Exclude
    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "actors")
    private Set<Film> films = new HashSet<>();


    public void addFilm(Film film) {
        films.add(film);
        film.getActors().add(this);
    }

    public void removeFilm(Film film) {
        films.remove(film);
        film.getActors().remove(this);
    }

    @UpdateTimestamp
    @Column(name = "last_update", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private ZonedDateTime lastUpdate;
}
