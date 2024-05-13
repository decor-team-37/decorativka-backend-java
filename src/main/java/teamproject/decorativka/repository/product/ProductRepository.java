package teamproject.decorativka.repository.product;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import teamproject.decorativka.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
    Page<Product> getAllByDeletedFalse(Pageable pageable);

    Optional<Product> findByIdAndDeletedFalse(Long id);

    List<Product> getAllByIdInAndDeletedFalse(List<Long> id);

    Page<Product> getAllByCategoryIdAndDeletedFalse(Long id,
                                                    Pageable pageable);
}
