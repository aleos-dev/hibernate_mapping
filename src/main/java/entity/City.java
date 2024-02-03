package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "city", indexes = {@Index(name = "index_city_country_id", columnList = "country_id")})
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "City name cannot be black.")
    @Size(max = 50, message = "City name must not exceed 50 characters.")
    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false, foreignKey = @ForeignKey(name = "fk_city_country"))
    private Country country;

    @Column(name = "last_update")
    @UpdateTimestamp
    private ZonedDateTime lastUpdate;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o instanceof City that) {
            return name.equalsIgnoreCase(that.name);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
