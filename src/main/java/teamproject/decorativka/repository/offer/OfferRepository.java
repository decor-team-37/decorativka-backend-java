package teamproject.decorativka.repository.offer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import teamproject.decorativka.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long>,
        JpaSpecificationExecutor<Offer> {
    Page<Offer> getAllByTypeId(Long id, Pageable pageable);
}
