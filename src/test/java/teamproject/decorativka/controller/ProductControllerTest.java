package teamproject.decorativka.controller;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.junit.jupiter.CitrusExtension;
import com.consol.citrus.dsl.runner.TestRunner;
import javax.sql.DataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import teamproject.decorativka.config.CustomPostgreSqlContainer;
import teamproject.decorativka.dto.product.ProductCreateRequestDto;
import teamproject.decorativka.dto.product.ProductResponseDto;
import teamproject.decorativka.model.Category;
import teamproject.decorativka.model.Product;
import teamproject.decorativka.service.NovaPoshtaCityParserService;
import teamproject.decorativka.service.ProductService;
import teamproject.decorativka.telegram.TelegramBot;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(CitrusExtension.class)
public class ProductControllerTest {
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
    private static final String VALID_DESCRIPTION = "This is valid description";
    private static final List<String> VALID_IMAGES_URLS = List.of("https://example.com/image1.jpg",
            "https://example.com/image2.jpg",
            "https://example.com/image3.jpg");
    private static final CustomPostgreSqlContainer postgreSqlContainer
            = CustomPostgreSqlContainer.getInstance();


    @Autowired
    private DataSource dataSource;
    @Autowired
    private ProductService productService;

    static {
        postgreSqlContainer.start();
    }

    @MockBean
    private JavaMailSender mailSender;
    @MockBean
    private TelegramBot telegramBot;
    @MockBean
    private NovaPoshtaCityParserService novaPoshtaCityParserService;

    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeAll
    static void setup(@Autowired WebApplicationContext applicationContext,
                      @CitrusResource TestRunner runner,
                      @Autowired DataSource dataSource
                      ) {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        CustomPostgreSqlContainer.getInstance().start();
        runner.sql(action -> action.dataSource(dataSource)
                .sqlResource("classpath:database/product/delete-all-from-db.sql"));
        runner.sql(action -> action.dataSource(dataSource)
                .sqlResource("classpath:database/category/create-two-categoty.sql"));
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSqlContainer::getUsername);
        registry.add("spring.datasource.password", postgreSqlContainer::getPassword);
    }

    @BeforeEach
    void beforeEach(@CitrusResource TestRunner runner) {
        runner.sql(action -> action.dataSource(dataSource)
                .sqlResource("classpath:database/product/delete-all-from-db.sql"));
    }

    @Test
    @CitrusTest
    void getAllProduct_ValidPageable_ValidProductResponseDto(@CitrusResource TestRunner runner) throws Exception {
        runner.sql(action -> action.dataSource(dataSource)
                .sqlResource("classpath:database/product/add-three-products.sql"));
        ProductResponseDto validProductResponseDto = createValidProductResponseDto();
        String jsonRequest = objectMapper.writeValueAsString(validProductResponseDto);
        List<ProductResponseDto> expected = List.of(validProductResponseDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/products?page=0&size=10&sort=name,asc")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        ProductResponseDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                ProductResponseDto[].class);
        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }
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
                VALID_TYPE,
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
                VALID_TONE,
                VALID_ROOM,
                VALID_DESCRIPTION,
                VALID_IMAGES_URLS
        );
    }
}
