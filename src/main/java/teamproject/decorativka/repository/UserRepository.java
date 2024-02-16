package teamproject.decorativka.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.decorativka.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
