package teamproject.decorativka.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import teamproject.decorativka.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.deleted = false")
    List<Order> findAllWithOrderItems(Pageable pageable);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems"
            + " WHERE o.id = :id AND o.deleted = false")
    Optional<Order> findByIdAndDeletedFalse(Long id);
}
