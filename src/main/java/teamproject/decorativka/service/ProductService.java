package teamproject.decorativka.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import teamproject.decorativka.dto.product.ProductCreateRequestDto;
import teamproject.decorativka.dto.product.ProductResponseDto;
import teamproject.decorativka.model.Product;

public interface ProductService {
    ProductResponseDto createProduct(ProductCreateRequestDto requestDto);

    List<ProductResponseDto> getAllProducts(Pageable pageable);

    ProductResponseDto getProductDto(Long id);

    Product getProduct(Long id);

    ProductResponseDto updateProduct(Long id, ProductCreateRequestDto requestDto);

    void deleteProduct(Long id);
}
