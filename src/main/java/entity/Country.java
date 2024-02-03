package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "country", uniqueConstraints = @UniqueConstraint(name = "uq_country_name", columnNames = "name"))
@SequenceGenerator(name = "country_seq", sequenceName = "country_id_seq", allocationSize = 1)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_seq")
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "Country name cannot be blank")
    @Size(max = 50, message = "Country name must not exceed 50 characters")
    private String name;

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "country", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @ToString.Exclude
    private List<City> cities = new ArrayList<>();

    @Column(name = "last_update")
    @UpdateTimestamp
    private ZonedDateTime lastUpdate;

    public void addCity(City city) {
        cities.add(city);
        city.setCountry(this);
    }

    public void removeCity(City city) {
        cities.remove(city);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o instanceof Country that) {
            return name.equalsIgnoreCase(that.name);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
