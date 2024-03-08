package teamproject.decorativka.repository.offer;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import teamproject.decorativka.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long>,
        JpaSpecificationExecutor<Offer> {
    Optional<Offer> findByIdAndDeletedFalse(Long id);

    List<Offer> getAllByDeletedFalse(Pageable pageable);
}
