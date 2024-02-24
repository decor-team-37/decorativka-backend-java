package teamproject.decorativka.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import teamproject.decorativka.dto.product.ProductCreateRequestDto;
import teamproject.decorativka.dto.product.ProductResponseDto;
import teamproject.decorativka.mapper.ProductMapper;
import teamproject.decorativka.model.Category;
import teamproject.decorativka.model.Product;
import teamproject.decorativka.repository.CategoryRepository;
import teamproject.decorativka.repository.ProductRepository;
import teamproject.decorativka.service.ProductService;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDto createProduct(ProductCreateRequestDto requestDto) {
        Product model = productMapper.toModel(requestDto);
        model.setCategory(resolveCategory(requestDto.categoryId()));
        return productMapper.toDto(productRepository.save(model));
    }

    @Override
    public List<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productRepository.getAllByDeletedFalse(pageable).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductResponseDto getProductDto(Long id) {
        return productMapper.toDto(getProduct(id));
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductCreateRequestDto requestDto) {
        Product productToUpdate = getProduct(id);
        productMapper.updateProductFromDto(requestDto, productToUpdate);
        return productMapper.toDto(productRepository.save(productToUpdate));
    }

    @Override
    public void deleteProduct(Long id) {
        Product productToDelete = getProduct(id);
        productToDelete.setDeleted(true);
        productRepository.save(productToDelete);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find product with id: " + id)
        );
    }

    private Category resolveCategory(Long id) {
        return categoryRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category with id: " + id)
        );
    }
}
