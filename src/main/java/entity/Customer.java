package entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "customer", indexes = {
        @Index(name = "index_customer_address", columnList = "address_id"),
        @Index(name = "index_customer_store", columnList = "store_id"),
        @Index(name = "index_customer_last_name", columnList = "last_name")},
        uniqueConstraints = {@UniqueConstraint(name = "uq_customer_email", columnNames = "email")})
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(name = "fk_customer_store_id"))
    private Store store;

    @ToString.Exclude
    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Payment> payments = new ArrayList<>();

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 45, message = "First name must not exceed 45 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 45, message = "Last name must not exceed 45 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToOne
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "fk_customer_address"))
    private Address address;

    @Size(max = 50, message = "Email must not exceed 50 characters")
    private String email;

    @Builder.Default
    @Column(name = "active")
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "create_date")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_update")
    private ZonedDateTime lastUpdate;


    private void addPayment(Payment payment) {
        payments.add(payment);
        payment.setCustomer(this);
    }

    public void removePayment(Payment payment) {
        payments.remove(payment);
    }
}
