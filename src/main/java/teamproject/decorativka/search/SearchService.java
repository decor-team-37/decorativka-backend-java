package teamproject.decorativka.search;

import org.springframework.data.jpa.domain.Specification;
import teamproject.decorativka.model.Offer;
import teamproject.decorativka.model.Product;

public interface SearchService {
    Specification<Offer> buildOfferSpecification(OfferSearchParameters params);

    Specification<Product> buildProductSpecification(ProductSearchParameters params);
}
