package teamproject.decorativka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.decorativka.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
