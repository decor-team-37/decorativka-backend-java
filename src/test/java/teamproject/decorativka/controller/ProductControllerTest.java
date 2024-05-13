package teamproject.decorativka.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.junit.jupiter.CitrusExtension;
import com.consol.citrus.dsl.runner.TestRunner;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;
import teamproject.decorativka.config.CustomPostgreSqlContainer;
import teamproject.decorativka.dto.product.ProductCreateRequestDto;
import teamproject.decorativka.dto.product.ProductResponseDto;
import teamproject.decorativka.search.ProductSearchParameters;
import teamproject.decorativka.service.NovaPoshtaCityParserService;
import teamproject.decorativka.telegram.TelegramBot;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(CitrusExtension.class)
public class ProductControllerTest {
    private static final Long VALID_ID = 1L;
    private static final CustomPostgreSqlContainer postgreSqlContainer
            = CustomPostgreSqlContainer.getInstance();
    private static MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    static {
        postgreSqlContainer.start();
    }

    @MockBean
    private JavaMailSender mailSender;
    @MockBean
    private TelegramBot telegramBot;
    @MockBean
    private NovaPoshtaCityParserService novaPoshtaCityParserService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup(@Autowired WebApplicationContext applicationContext) {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        CustomPostgreSqlContainer.getInstance().start();

    }

    @Data
    public static class PageResponse<T> {
        private List<T> content;
        private int totalElements;
        private int totalPages;
        private boolean last;
        private boolean first;
        private int numberOfElements;
        private int size;
        private int number;
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
                .sqlResource("classpath:database/delete-all-from-db.sql"));
        runner.sql(action -> action.dataSource(dataSource)
                .sqlResource("classpath:database/category/create-two-category.sql"));
    }

    @Test
    @CitrusTest
    void getAllProduct_ValidPageable_ValidProductResponseDto(
            @CitrusResource TestRunner runner)
            throws Exception {
        runner.sql(action -> action.dataSource(dataSource)
                .sqlResource("classpath:database/product/add-three-products.sql"));

        List<ProductResponseDto> expected = generateThreeProductResponseDtoList();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/products?page=0&size=10&sort=name,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        PageResponse<ProductResponseDto> pageResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {});

        // Перевірте результати
        assertEquals(3, pageResponse.getContent().size());
        assertEquals(expected, pageResponse.getContent());
    }

    @Test
    @CitrusTest
    void getAllProductsByCategoryId_ValidId_ValidResult(
            @CitrusResource TestRunner runner) throws Exception {
        runner.sql(action -> action.dataSource(dataSource)
                .sqlResource("classpath:database/product/add-three-products.sql"));

        List<ProductResponseDto> expected = generateTwoProductResponseDtoList();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/products/all/" + VALID_ID + "?page=0&size=10&sort=name,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        PageResponse<ProductResponseDto> pageResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {});

        assertEquals(2, pageResponse.getContent().size());

        EqualsBuilder.reflectionEquals(expected, pageResponse.getContent());
    }

    @Test
    @CitrusTest
    void getProduct_ValidId_ValidResult(@CitrusResource TestRunner runner) throws Exception {
        runner.sql(action -> action.dataSource(dataSource)
                .sqlResource("classpath:database/product/add-three-products.sql"));

        ProductResponseDto expected = generateOneProductResponseDto();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/products/" + VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        ProductResponseDto actual = objectMapper.readValue(mvcResult
                        .getResponse().getContentAsByteArray(),
                ProductResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual,
                "The expected and actual JSON responses should match.");

    }

    @CitrusTest
    void searchProductsWithPriceRange_ReturnsFilteredProducts(
            @CitrusResource TestRunner runner) throws Exception {
        // Arrange
        BigDecimal minPrice = new BigDecimal("20.00");
        BigDecimal maxPrice = new BigDecimal("40.00");

        ProductSearchParameters searchParams = new ProductSearchParameters();
        searchParams.setMinPrice(minPrice);
        searchParams.setMaxPrice(maxPrice);

        String jsonRequest = objectMapper.writeValueAsString(searchParams);
        List<ProductResponseDto> expected = generateProductResponseDtoListForPriceRange();

        runner.sql(action -> action.dataSource(dataSource)
                .sqlResource("classpath:database/product/add-three-products.sql"));

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/products/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // Assert
        PageResponse<ProductResponseDto> pageResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {});

        List<ProductResponseDto> actual = pageResponse.getContent();
        assertEquals(expected.size(), actual.size(),
                "The number of returned products should match the expected size.");
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @CitrusTest
    @Test
    void createNewProduct_ValidRequestDto_ValidRecordInDb(
            @CitrusResource TestRunner runner) throws Exception {
        ProductCreateRequestDto requestDto = createRequestDto();
        String request = objectMapper.writeValueAsString(requestDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/product/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        ProductResponseDto actual = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsByteArray(), ProductResponseDto.class);
        assertEquals(generateOneProductResponseDto(), actual);
        runner.query(sqlAction -> sqlAction.dataSource(dataSource)
                .statement("SELECT COUNT(*) AS product_count FROM products")
                .validate("product_count", "1"));

    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @CitrusTest
    @Test
    void updateProductInfo_ValidRequestDto_ValidUpdateInDb(
            @CitrusResource TestRunner runner) throws Exception {
        runner.sql(action -> action.dataSource(dataSource)
                .sqlResource("classpath:database/product/add-three-products.sql"));
        Long productIdToUpdate = VALID_ID;
        ProductCreateRequestDto updateRequest = createRequestDtoForUpdate();
        String request = objectMapper.writeValueAsString(updateRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/product/update/" + productIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        ProductResponseDto actual = objectMapper.readValue(mvcResult
                        .getResponse().getContentAsByteArray(),
                ProductResponseDto.class);
        assertEquals(updateRequest.name(), actual.name());

        runner.query(sqlAction -> sqlAction.dataSource(dataSource)
                .statement("SELECT name FROM products WHERE id = " + productIdToUpdate)
                .validate("name", updateRequest.name()));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @CitrusTest
    @Test
    void deleteProduct_ValidId_ProductDeleted(
            @CitrusResource TestRunner runner) throws Exception {
        Long productIdToDelete = VALID_ID;
        runner.sql(action -> action.dataSource(dataSource)
                .sqlResource("classpath:database/product/add-three-products.sql"));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/product/delete/" + productIdToDelete))
                .andExpect(status().isNoContent());

        runner.query(sqlAction -> sqlAction.dataSource(dataSource)
                .statement("SELECT deleted FROM products WHERE id = " + productIdToDelete)
                .validate("deleted", "true"));
    }

    private ProductCreateRequestDto createRequestDto() {
        return new ProductCreateRequestDto("Product 1",
                1L,
                new BigDecimal("19.99"),
                "Country 1",
                "Producer 1",
                "Collection 1",
                "Type 1",
                "Tone 1",
                "Room 1",
                "Description 1",
                Collections.emptyList()
        );
    }

    private ProductCreateRequestDto createRequestDtoForUpdate() {
        return new ProductCreateRequestDto("Product 1",
                1L,
                new BigDecimal("19.99"),
                "Country 1",
                "Producer 1",
                "Collection 1",
                "Type 1",
                "Tone 1",
                "Room 1",
                "Description 1",
                Collections.emptyList()
        );
    }

    private List<ProductResponseDto> generateProductResponseDtoListForPriceRange() {
        return List.of(
                new ProductResponseDto(
                        2L, "Product 2", 2L,
                        new BigDecimal("29.99"), "Country 2",
                        "Producer 2",
                        "Collection 2", "Type 2",
                        "Tone 2", "Room 2",
                        "Description 2", Collections.emptyList()
                ),
                new ProductResponseDto(
                        3L, "Product 3", 1L,
                        new BigDecimal("39.99"), "Country 3", "Producer 3",
                        "Collection 3", "Type 3",
                        "Tone 3", "Room 3",
                        "Description 3", Collections.emptyList()
                )
        );
    }

    private List<ProductResponseDto> generateThreeProductResponseDtoList() {
        return List.of(
                new ProductResponseDto(
                        1L,
                        "Product 1",
                        1L,
                        new BigDecimal("19.99"),
                        "Country 1",
                        "Producer 1",
                        "Collection 1",
                        "Type 1",
                        "Tone 1",
                        "Room 1",
                        "Description 1",
                        Collections.emptyList()
                ), new ProductResponseDto(
                        2L,
                        "Product 2",
                        2L,
                        new BigDecimal("29.99"),
                        "Country 2",
                        "Producer 2",
                        "Collection 2",
                        "Type 2",
                        "Tone 2",
                        "Room 2",
                        "Description 2",
                        Collections.emptyList()
                ),
                 new ProductResponseDto(
                        3L,
                        "Product 3",
                        1L,
                        new BigDecimal("39.99"),
                        "Country 3",
                        "Producer 3",
                        "Collection 3",
                        "Type 3",
                        "Tone 3",
                        "Room 3",
                        "Description 3",
                        Collections.emptyList()
                ));
    }

    private List<ProductResponseDto> generateTwoProductResponseDtoList() {
        return List.of(new ProductResponseDto(
                        1L,
                        "Product 1",
                        1L,
                        new BigDecimal("19.99"),
                        "Country 1",
                        "Producer 1",
                        "Collection 1",
                        "Type 1",
                        "Tone 1",
                        "Room 1",
                        "Description 1",
                        Collections.emptyList()
                ),
                 new ProductResponseDto(
                        2L,
                        "Product 3",
                        1L,
                        new BigDecimal("39.99"),
                        "Country 3",
                        "Producer 3",
                        "Collection 3",
                        "Type 3",
                        "Tone 3",
                        "Room 3",
                        "Description 3",
                        Collections.emptyList()
                ));
    }

    private ProductResponseDto generateOneProductResponseDto() {
        return new ProductResponseDto(
                1L,
                "Product 1",
                1L,
                new BigDecimal("19.99"),
                "Country 1",
                "Producer 1",
                "Collection 1",
                "Type 1",
                "Tone 1",
                "Room 1",
                "Description 1",
                Collections.emptyList()
        );
    }
}
