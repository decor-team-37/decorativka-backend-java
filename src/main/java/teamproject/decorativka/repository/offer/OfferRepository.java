package teamproject.decorativka.repository.offer;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamproject.decorativka.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long>,
        JpaSpecificationExecutor<Offer> {
    Page<Offer> getAllByTypeId(Long id, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Offer o WHERE o.id = :id")
    void deleteOfferById(@Param("id") Long id);
}
