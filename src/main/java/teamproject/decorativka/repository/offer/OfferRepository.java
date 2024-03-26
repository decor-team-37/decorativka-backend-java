package teamproject.decorativka.repository.offer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import teamproject.decorativka.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long>,
        JpaSpecificationExecutor<Offer> {
    List<Offer> getAllByTypeId(Long id);
}
