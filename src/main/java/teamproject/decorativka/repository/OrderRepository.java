package teamproject.decorativka.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.decorativka.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByDeletedFalse(Pageable pageable);

    Optional<Order> findByIdAndDeletedFalse(Long id);
}
