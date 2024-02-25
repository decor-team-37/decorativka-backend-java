package teamproject.decorativka.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String patronymic;
    @Column(nullable = false)
    private String shippingAddress;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    private String comment;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    private BigDecimal total;
    @Column(nullable = false)
    private LocalDateTime orderDate;
    @OneToMany(mappedBy = "order")
    @EqualsAndHashCode.Exclude
    private Set<OrderItem> orderItems = new HashSet<>();
    @Column(nullable = false)
    private boolean deleted = false;

    public enum Status {
        COMPLETED,
        PENDING,
        DELIVERED,
        PROCESSING
    }
}
