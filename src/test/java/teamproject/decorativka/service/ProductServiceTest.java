package teamproject.decorativka.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamproject.decorativka.dto.product.ProductCreateRequestDto;
import teamproject.decorativka.dto.product.ProductResponseDto;
import teamproject.decorativka.mapper.ProductMapper;
import teamproject.decorativka.model.Category;
import teamproject.decorativka.model.Product;
import teamproject.decorativka.repository.CategoryRepository;
import teamproject.decorativka.repository.product.ProductRepository;
import teamproject.decorativka.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    private static final Long VALID_ID = 1L;
    private static final Long NOT_VALID_ID = -1L;
    private static final String VALID_NAME = "Product name";
    private static final String VALID_CATEGORY_NAME = "Category name";
    private static final BigDecimal VALID_PRICE = BigDecimal.TEN;
    private static final String VALID_COUNTRY = "Ukraine";
    private static final String VALID_COLLECTION = "Collection 1";
    private static final String VALID_PRODUCER = "Valid producer";
    private static final String VALID_TONE = "Dark";
    private static final String VALID_TYPE = "Decorative";
    private static final String VALID_ROOM = "General room";
    private static final String VALID_CODE = "ABC123";
    private static final String VALID_DESCRIPTION = "This is valid description";
    private static final List<String> VALID_IMAGES_URLS = List.of("https://example.com/image1.jpg",
            "https://example.com/image2.jpg",
            "https://example.com/image3.jpg");

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private CategoryRepository categoryRepository;

    private Product createValidProduct() {
        Product product = new Product();
        product.setId(VALID_ID);
        product.setCountry(VALID_COUNTRY);
        product.setName(VALID_NAME);
        product.setCollection(VALID_COLLECTION);
        product.setRoom(VALID_ROOM);
        product.setPrice(VALID_PRICE);
        product.setTone(VALID_TONE);
        product.setImageUrl(VALID_IMAGES_URLS);
        product.setProducer(VALID_PRODUCER);
        product.setCode(VALID_CODE);
        product.setCategory(createValidCategory());
        return product;
    }

    private Category createValidCategory() {
        Category category = new Category();
        category.setId(VALID_ID);
        category.setName(VALID_CATEGORY_NAME);
        category.setDescription(VALID_DESCRIPTION);
        return category;
    }

    private ProductCreateRequestDto createValidProductRequestDto() {
        return new ProductCreateRequestDto(
                VALID_NAME,
                VALID_ID,
                VALID_PRICE,
                VALID_COUNTRY,
                VALID_PRODUCER,
                VALID_COLLECTION,
                VALID_TONE,
                VALID_TYPE,
                VALID_CODE,
                VALID_ROOM,
                VALID_DESCRIPTION,
                VALID_IMAGES_URLS
        );
    }

    private ProductCreateRequestDto createNotValidProductRequestDto() {
        return new ProductCreateRequestDto(
                VALID_NAME,
                NOT_VALID_ID,
                VALID_PRICE,
                VALID_COUNTRY,
                VALID_PRODUCER,
                VALID_COLLECTION,
                VALID_TYPE,
                VALID_CODE,
                VALID_TONE,
                VALID_ROOM,
                VALID_DESCRIPTION,
                VALID_IMAGES_URLS
        );
    }

    private ProductResponseDto createValidProductResponseDto() {
        return new ProductResponseDto(
                VALID_ID,
                VALID_NAME,
                VALID_ID,
                VALID_PRICE,
                VALID_COUNTRY,
                VALID_PRODUCER,
                VALID_COLLECTION,
                VALID_TYPE,
                VALID_CODE,
                VALID_TONE,
                VALID_ROOM,
                VALID_DESCRIPTION,
                VALID_IMAGES_URLS
        );
    }

    @Test
    @DisplayName("Verify createProduct() method works")
    public void createProduct_ValidRequestDto_ValidResponseDto() {
        ProductResponseDto expected = createValidProductResponseDto();
        Product validProduct = createValidProduct();
        ProductCreateRequestDto validProductRequestDto = createValidProductRequestDto();
        Category validCategory = createValidCategory();
        when(categoryRepository.findByIdAndDeletedFalse(VALID_ID))
                .thenReturn(Optional.of(validCategory));
        when(productMapper.toModel(validProductRequestDto)).thenReturn(validProduct);
        when(productRepository.save(validProduct)).thenReturn(validProduct);
        when(productMapper.toDto(validProduct)).thenReturn(expected);

        ProductResponseDto actual = productService.createProduct(validProductRequestDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify createProduct() throw exception with not valid category id")
    public void createProduct_NotValidCategoryId_EntityNotFoundException() {
        Product validProduct = createValidProduct();
        ProductCreateRequestDto notValidProductRequestDto = createNotValidProductRequestDto();
        when(productMapper.toModel(notValidProductRequestDto)).thenReturn(validProduct);
        when(categoryRepository.findByIdAndDeletedFalse(NOT_VALID_ID))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> productService.createProduct(notValidProductRequestDto));
    }

    /*  @Test
      @DisplayName("Verify getAllProducts() method works")
      public void getAllProducts_ValidPageable_ValidListResponseDto() {
          List<Product> validProductList = List.of(createValidProduct());
          Pageable pageable = PageRequest.of(0, 10);
          List<ProductResponseDto> expected = List.of(createValidProductResponseDto());
          when(productRepository.getAllByDeletedFalse(pageable))
                  .thenReturn(validProductList);
          when(productMapper.toDto(createValidProduct()))
                  .thenReturn(createValidProductResponseDto());

          List<ProductResponseDto> actual = productService.getAllProducts(pageable);

          assertEquals(expected, actual);
      }
  */
    @Test
    @DisplayName("Verify getProduct() method works")
    public void getProduct_ValidID_ValidResponseDto() {
        Product validProduct = createValidProduct();
        ProductResponseDto expected = createValidProductResponseDto();
        when(productRepository.findByIdAndDeletedFalse(VALID_ID))
                .thenReturn(Optional.of(validProduct));
        when(productMapper.toDto(validProduct)).thenReturn(expected);

        ProductResponseDto actual = productService.getProduct(VALID_ID);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify getProduct() throw exception with not valid id")
    public void getProduct_NotValidID_EntityNotFoundException() {
        when(productRepository.findByIdAndDeletedFalse(NOT_VALID_ID))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> productService.getProduct(NOT_VALID_ID));
    }

    @Test
    @DisplayName("Verify updateProduct() method works")
    public void updateProduct_ValidIdAndRequestDto_UpdatedRequestDto() {
        ProductResponseDto expected = createValidProductResponseDto();
        Product validProduct = createValidProduct();
        ProductCreateRequestDto validProductRequestDto = createValidProductRequestDto();
        when(productRepository.findByIdAndDeletedFalse(VALID_ID))
                .thenReturn(Optional.of(validProduct));
        when(productRepository.save(validProduct)).thenReturn(validProduct);
        when(productMapper.toDto(validProduct)).thenReturn(expected);

        ProductResponseDto actual = productService.updateProduct(
                VALID_ID, validProductRequestDto);

        verify(productMapper).updateProductFromDto(
                validProductRequestDto, validProduct);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify deleteProduct() method works")
    public void deteleProduct_ValidId_DeletedTrue() {
        Product expected = createValidProduct();
        expected.setDeleted(true);
        when(productRepository.findByIdAndDeletedFalse(VALID_ID))
                .thenReturn(Optional.of(createValidProduct()));

        productService.deleteProduct(VALID_ID);

        verify(productRepository).save(expected);
    }

    @Test
    @DisplayName("Verify getAllByIds() method works")
    public void getAllByIds_ValidIds_ListProducts() {
        List<Product> expected = List.of(createValidProduct());
        List<Long> ids = List.of(VALID_ID);
        when(productRepository.getAllByIdInAndDeletedFalse(ids))
                .thenReturn(expected);

        List<Product> actual = productService.getAllByIds(ids);

        assertEquals(expected, actual);
    }
}
