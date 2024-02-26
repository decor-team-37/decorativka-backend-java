package teamproject.decorativka.service;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamproject.decorativka.mapper.ProductMapper;
import teamproject.decorativka.model.Product;
import teamproject.decorativka.repository.CategoryRepository;
import teamproject.decorativka.repository.ProductRepository;
import teamproject.decorativka.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    private static final Long VALID_ID = 1L;
    private static final String VALID_NAME = "Product name";
    private static final BigDecimal VALID_PRICE = BigDecimal.TEN;
    private static final String VALID_COUNTRY = "Ukraine";
    private static final String VALID_COLLECTION = "Collection 1";
    private static final String VALID_PRODUCER = "Valid producer";
    private static final String VALID_TONE = "Dark";
    private static final String VALID_TYPE = "Decorative";
    private static final String VALID_ROOM = "General room";
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
        return product;
    }
}
