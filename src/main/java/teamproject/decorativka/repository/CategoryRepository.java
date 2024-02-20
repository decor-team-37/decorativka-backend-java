package teamproject.decorativka.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.decorativka.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByIdAndDeletedFalse(Long id);

    List<Category> getAllByDeletedFalse();
}
