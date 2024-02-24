package teamproject.decorativka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.decorativka.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
