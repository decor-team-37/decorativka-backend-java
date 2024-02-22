package teamproject.decorativka.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.decorativka.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Optional<Offer> findByIdAndDeletedFalse(Long id);

    List<Offer> getAllByDeletedFalse(Pageable pageable);
}
