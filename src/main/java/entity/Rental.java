package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "rental",
        indexes = {
                @Index(name = "index_rental_customer_id", columnList = "customer_id"),
                @Index(name = "index_rental_inventory_id", columnList = "inventory_id"),
                @Index(name = "index_rental_store_id", columnList = "store_id"),
                @Index(name = "index_rental_rental_date", columnList = "rental_date")
        }
)
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inventory_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rental_inventory"))
    private Inventory inventory;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rental_customer"))
    private Customer customer;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "staff_id", nullable = false, foreignKey = @ForeignKey(name = "fk nullable = false,_rental_staff_manager"))
    private StaffManager staffManager;

    @OneToOne(mappedBy = "rental")
    private Payment payment;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rental_store"))
    private Store store;

    @CreationTimestamp
    @Column(name = "rental_date")
    private ZonedDateTime rentalDate;

    @Column(name = "return_date")
    private ZonedDateTime returnDate;

    @UpdateTimestamp
    @Column(name = "last_update")
    private ZonedDateTime lastUpdate;
}
