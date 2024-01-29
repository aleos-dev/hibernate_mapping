package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "payment",
        indexes = {
                @Index(name = "index_payment_customer_id", columnList = "customer_id"),
                @Index(name = "index_payment_staff_id", columnList = "staff_id"),
                @Index(name = "index_payment_rental_id", columnList = "rental_id"),
        })
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_payment_customer_id"))
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "staff_id", nullable = false, foreignKey = @ForeignKey(name = "fk_payment_staff_manager_id"))
    private StaffManager manager;

    @OneToOne
    @JoinColumn(name = "rental_id", foreignKey = @ForeignKey(name = "fk_payment_rental_id"))
    private Rental rental;

    @Column(name = "amount", precision = 5, scale = 2, nullable = false)
    private BigDecimal amount = BigDecimal.valueOf(0);

    @CreationTimestamp
    @Column(name = "payment_date")
    private ZonedDateTime paymentDate;

    @UpdateTimestamp
    @Column(name = "last_Update")
    private ZonedDateTime lastUpdate;
}