package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "address",
        indexes = {@Index(name = "uq_address_city_id", columnList = "city_id")})
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"address", "districtName", "postalCode"})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 50, message = "Address must not exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String address;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false, foreignKey = @ForeignKey(name = "fk_address_city"))
    private City city;

    @Column(length = 50)
    @Size(max = 50, message = "Address2 must not exceed 50 characters")
    private String address2;

    @Column(name = "district", nullable = false, length = 20)
    @NotBlank(message = "District cannot be blank")
    @Size(max = 20, message = "District name must not exceed 20 characters")
    private String districtName;

    @Column(name = "postal_code", length = 5)
    private Integer postalCode;

    @Column(length = 20)
    @Size(max = 20, message = "Phone number must not exceed 20 digits")
    private String phone;

    @Column(name = "last_update")
    @UpdateTimestamp
    private ZonedDateTime lastUpdate;
}
