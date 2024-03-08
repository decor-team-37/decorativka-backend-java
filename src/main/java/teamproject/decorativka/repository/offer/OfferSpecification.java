package teamproject.decorativka.repository.offer;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import teamproject.decorativka.model.Offer;

@Component
public class OfferSpecification {
    public static Specification<Offer> hasName(List<String> names) {
        return (offer, cq, cb) -> names == null || names.isEmpty()
                ? null : offer.get("name").in(names);
    }
}
