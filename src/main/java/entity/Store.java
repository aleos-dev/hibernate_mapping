package entity;


import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "store", indexes = {@Index(name = "uq_store_address", columnList = "address_id")})
@ToString
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "store")
    private StaffManager staffManager;

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "fk_store_address"))
    private Address address;

    @OneToMany(mappedBy = "store", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @Setter(AccessLevel.PRIVATE)
    @Builder.Default
    @ToString.Exclude
    List<Customer> customers = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @Setter(AccessLevel.PRIVATE)
    @Builder.Default
    @ToString.Exclude
    List<Rental> rentalBook = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @Setter(AccessLevel.PRIVATE)
    @Builder.Default
    @ToString.Exclude
    List<Inventory> inventoryBook = new ArrayList<>();

    @UpdateTimestamp
    @Column(name = "last_update")
    private ZonedDateTime lastUpdate;

    public void addCustomer(Customer customer) {
        customers.add(customer);
        customer.setStore(this);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    public void addRental(Rental rental) {
        rentalBook.add(rental);
        rental.setStore(this);
    }

    public void removeRental(Rental rental) {
        rentalBook.remove(rental);
    }

    public void addInventory(Inventory inventory) {
        inventoryBook.add(inventory);
        inventory.setStore(this);
    }

    public void removeInventory(Inventory inventory) {
        inventoryBook.remove(inventory);
    }
}
