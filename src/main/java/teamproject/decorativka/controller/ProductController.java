package teamproject.decorativka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.decorativka.dto.product.ProductResponseDto;
import teamproject.decorativka.search.ProductSearchParameters;
import teamproject.decorativka.service.ProductService;

@Tag(name = "Product manager",
        description = "Endpoints to get info about products (access to all)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get all products",
            description = "Get list of all not deleted products")
    @Parameter(name = "page", description = "open specified page, default value = 0",
            required = true, example = "0")
    @Parameter(name = "size", description = "describes count element per page",
            required = true, example = "10")
    @Parameter(name = "sort", description = "add sort for schema field", example = "sort=price")
    @GetMapping
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @Operation(summary = "Get one specific product by id")
    @GetMapping("/{id}")
    public ProductResponseDto getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @Operation(summary = "Search products for specific parameters",
            description = "Get all products, which have certain params: "
                    + "name, price, country, producer, collection, tone, room, type")
    @GetMapping("/search")
    public Page<ProductResponseDto> getAllBySearchParams(ProductSearchParameters searchParameters,
                                                         Pageable pageable) {
        return productService.search(searchParameters, pageable);
    }

    @Operation(summary = "Get all product by category id")
    @GetMapping("/all/{id}")
    public Page<ProductResponseDto> getAllProductsByCategoryId(
            @PathVariable Long id,
            Pageable pageable
    ) {
        return productService.getAllProductsByCategoryId(id,pageable);
    }
}
