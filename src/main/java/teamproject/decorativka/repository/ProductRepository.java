package teamproject.decorativka.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.decorativka.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getAllByDeletedFalse(Pageable pageable);

    Optional<Product> findByIdAndDeletedFalse(Long id);

    List<Product> getAllByIdInAndDeletedFalse(List<Long> id);
}
