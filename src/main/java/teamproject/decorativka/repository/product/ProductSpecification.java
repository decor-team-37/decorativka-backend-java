package teamproject.decorativka.repository.product;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import teamproject.decorativka.model.Product;

@Component
public class ProductSpecification {
    public static Specification<Product> hasNames(List<String> names) {
        return (product, cq, cb) -> names == null || names.isEmpty()
                ? null : product.get("name").in(names);
    }

    public static Specification<Product> priceInRange(
            BigDecimal minPrice, BigDecimal maxPrice) {
        return (product, cq, cb) -> {
            if (minPrice != null && maxPrice != null) {
                return cb.between(product.get("price"), minPrice, maxPrice);
            }
            if (minPrice != null) {
                return cb.ge(product.get("price"), minPrice);
            }
            if (maxPrice != null) {
                return cb.le(product.get("price"), maxPrice);
            }
            return null;
        };
    }

    public static Specification<Product> hasCountries(List<String> countries) {
        return (product, cq, cb) -> countries == null || countries.isEmpty()
                ? null : product.get("country").in(countries);
    }

    public static Specification<Product> hasProducers(List<String> producers) {
        return (product, cq, cb) -> producers == null || producers.isEmpty()
                ? null : product.get("producer").in(producers);
    }

    public static Specification<Product> hasCollections(List<String> collections) {
        return (product, cq, cb) -> collections == null || collections.isEmpty()
                ? null : product.get("collection").in(collections);
    }

    public static Specification<Product> hasTone(List<String> tones) {
        return (product, cq, cb) -> tones == null || tones.isEmpty()
                ? null : product.get("tone").in(tones);
    }

    public static Specification<Product> hasType(List<String> types) {
        return (product, cq, cb) -> types == null || types.isEmpty()
                ? null : product.get("type").in(types);
    }

    public static Specification<Product> hasRoom(List<String> rooms) {
        return (product, cq, cb) -> rooms == null || rooms.isEmpty()
                ? null : product.get("room").in(rooms);
    }

}
