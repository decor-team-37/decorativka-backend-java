package teamproject.decorativka.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.decorativka.dto.product.ProductCreateRequestDto;
import teamproject.decorativka.dto.product.ProductResponseDto;
import teamproject.decorativka.model.Product;
import teamproject.decorativka.search.ProductSearchParameters;

public interface ProductService {
    ProductResponseDto createProduct(ProductCreateRequestDto requestDto);

    Page<ProductResponseDto> getAllProducts(Pageable pageable);

    ProductResponseDto getProduct(Long id);

    ProductResponseDto updateProduct(Long id, ProductCreateRequestDto requestDto);

    void deleteProduct(Long id);

    List<Product> getAllByIds(List<Long> ids);

    Page<ProductResponseDto> search(ProductSearchParameters searchParameters, Pageable pageable);

    Page<ProductResponseDto> getAllProductsByCategoryId(Long id,
                                                        Pageable pageable
    );
}

