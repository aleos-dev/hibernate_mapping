package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "staff", indexes = {
        @Index(name = "index_staff_address", columnList = "address_id"),
        @Index(name = "index_staff_store", columnList = "store_id")},
        uniqueConstraints = {@UniqueConstraint(name = "uq_store_staff", columnNames = "store_id"),
                @UniqueConstraint(name = "uq_store_email", columnNames = "email")})
@ToString
public class StaffManager {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "fk_staff_address"))
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(name = "fk_staff_store"))
    @MapsId
    private Store store;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 45, message = "Name must not exceed 45 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 45, message = "Name must not exceed 45 characters")
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 50, message = "Email must not exceed 50 characters")
    private String email;

    @Size(max = 16, message = "Username must not exceed 16 characters")
    private String username;

    @Size(min = 6, max = 40, message = "Password must be from 6 to 40 characters")
    @Column(nullable = false)
    private String password;

    @Builder.Default
    @Column(name = "active")
    private boolean isActive = true;

    @UpdateTimestamp
    @Column(name = "last_update")
    private ZonedDateTime lastUpdate;
}
