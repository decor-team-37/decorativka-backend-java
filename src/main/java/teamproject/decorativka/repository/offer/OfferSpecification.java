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

    public static Specification<Offer> hasCategoryId(List<Long> categoryIds) {
        return (offer, cq, cb) -> categoryIds == null || categoryIds.isEmpty()
                ? null : offer.get("type").get("id").in(categoryIds);
    }

    public static Specification<Offer> hasId(List<Long> ids) {
        return (offer, cq, cb) -> ids == null || ids.isEmpty()
                ? null : offer.get("id").in(ids);
    }
}
