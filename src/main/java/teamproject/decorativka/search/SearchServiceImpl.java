package teamproject.decorativka.search;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import teamproject.decorativka.model.Offer;
import teamproject.decorativka.model.Product;
import teamproject.decorativka.repository.offer.OfferSpecification;
import teamproject.decorativka.repository.product.ProductSpecification;

@Service
public class SearchServiceImpl implements SearchService {
    public Specification<Offer> buildOfferSpecification(OfferSearchParameters params) {
        Specification<Offer> spec = Specification.where(null);

        if (params.getName() != null && !params.getName().isEmpty()) {
            spec = spec.and(OfferSpecification.hasName(params.getName()));
        }
        if (params.getCategoryId() != null && !params.getCategoryId().isEmpty()) {
            spec = spec.and(OfferSpecification.hasCategoryId(params.getCategoryId()));
        }
        if (params.getId() != null && !params.getId().isEmpty()) {
            spec = spec.and(OfferSpecification.hasId(params.getId()));
        }
        return spec;
    }

    public Specification<Product> buildProductSpecification(ProductSearchParameters params) {
        Specification<Product> spec = Specification.where(null);

        if (params.getName() != null && !params.getName().isEmpty()) {
            spec = spec.and(ProductSpecification.hasNames(params.getName()));
        }
        if (params.getMinPrice() != null || params.getMaxPrice() != null) {
            spec = spec.and(ProductSpecification.priceInRange(
                    params.getMinPrice(), params.getMaxPrice()));
        }
        if (params.getCountry() != null && !params.getCountry().isEmpty()) {
            spec = spec.and(ProductSpecification.hasCountries(params.getCountry()));
        }
        if (params.getProducer() != null && !params.getProducer().isEmpty()) {
            spec = spec.and(ProductSpecification.hasProducers(params.getProducer()));
        }
        if (params.getCollection() != null && !params.getCollection().isEmpty()) {
            spec = spec.and(ProductSpecification.hasCollections(params.getCollection()));
        }
        if (params.getTone() != null && !params.getTone().isEmpty()) {
            spec = spec.and(ProductSpecification.hasTone(params.getTone()));
        }
        if (params.getType() != null && !params.getType().isEmpty()) {
            spec = spec.and(ProductSpecification.hasType(params.getType()));
        }
        if (params.getRoom() != null && !params.getRoom().isEmpty()) {
            spec = spec.and(ProductSpecification.hasRoom(params.getRoom()));
        }
        if (params.getCode() != null && !params.getCode().isEmpty()) {
            spec = spec.and(ProductSpecification.hasCode(params.getCode()));
        }
        if (params.getCategoryId() != null && !params.getCategoryId().isEmpty()) {
            spec = spec.and(ProductSpecification.hasCategoryId(params.getCategoryId()));
        }
        if (params.getId() != null && !params.getId().isEmpty()) {
            spec = spec.and(ProductSpecification.hasId(params.getId()));
        }
        return spec;
    }
}
