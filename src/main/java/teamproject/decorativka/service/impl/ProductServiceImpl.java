package teamproject.decorativka.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import teamproject.decorativka.dto.product.ProductCreateRequestDto;
import teamproject.decorativka.dto.product.ProductResponseDto;
import teamproject.decorativka.mapper.ProductMapper;
import teamproject.decorativka.model.Category;
import teamproject.decorativka.model.Product;
import teamproject.decorativka.repository.CategoryRepository;
import teamproject.decorativka.repository.product.ProductRepository;
import teamproject.decorativka.search.ProductSearchParameters;
import teamproject.decorativka.search.SearchService;
import teamproject.decorativka.service.ProductService;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final SearchService searchService;

    @Override
    public ProductResponseDto createProduct(ProductCreateRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setCategory(resolveCategory(requestDto.categoryId()));
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productRepository.getAllByDeletedFalse(pageable).map(productMapper::toDto);
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        return productMapper.toDto(getProductById(id));
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductCreateRequestDto requestDto) {
        Product productToUpdate = getProductById(id);
        productMapper.updateProductFromDto(requestDto, productToUpdate);
        return productMapper.toDto(productRepository.save(productToUpdate));
    }

    @Override
    public void deleteProduct(Long id) {
        Product productToDelete = getProductById(id);
        productToDelete.setDeleted(true);
        productRepository.save(productToDelete);
    }

    @Override
    public List<Product> getAllByIds(List<Long> ids) {
        return productRepository.getAllByIdInAndDeletedFalse(ids);
    }

    @Override
    public Page<ProductResponseDto> search(ProductSearchParameters searchParameters,
                                           Pageable pageable) {
        Specification<Product> productSpecification
                = searchService.buildProductSpecification(searchParameters);
        return productRepository.findAllAndDeletedFalse(productSpecification, pageable)
                .map(productMapper::toDto);
    }

    @Override
    public Page<ProductResponseDto> getAllProductsByCategoryId(
            Long id,
            Pageable pageable) {
        resolveCategory(id);
        return productRepository
                .getAllByCategoryIdAndDeletedFalse(id, pageable)
                .map(productMapper::toDto);
    }

    private Product getProductById(Long id) {
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
