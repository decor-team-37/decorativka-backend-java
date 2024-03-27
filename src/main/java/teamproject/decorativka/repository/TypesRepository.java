package teamproject.decorativka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.decorativka.model.Type;

public interface TypesRepository extends JpaRepository<Type, Long> {
}
