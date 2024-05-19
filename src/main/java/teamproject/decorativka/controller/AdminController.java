package teamproject.decorativka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import teamproject.decorativka.dto.category.CategoryCreateRequestDto;
import teamproject.decorativka.dto.category.CategoryResponseDto;
import teamproject.decorativka.dto.login.LoginRequestDto;
import teamproject.decorativka.dto.login.LoginResponseDto;
import teamproject.decorativka.dto.offer.OfferCreateRequestDto;
import teamproject.decorativka.dto.offer.OfferResponseDto;
import teamproject.decorativka.dto.order.OrderResponseDto;
import teamproject.decorativka.dto.order.OrderStatusUpdateRequestDto;
import teamproject.decorativka.dto.product.ProductCreateRequestDto;
import teamproject.decorativka.dto.product.ProductResponseDto;
import teamproject.decorativka.dto.type.TypeCreateRequestDto;
import teamproject.decorativka.dto.type.TypeResponseDto;
import teamproject.decorativka.secutiry.AuthenticationService;
import teamproject.decorativka.service.CategoryService;
import teamproject.decorativka.service.OfferService;
import teamproject.decorativka.service.OrderService;
import teamproject.decorativka.service.ProductService;
import teamproject.decorativka.service.TypeService;

@Tag(name = "Controller for admin panel", description = "Endpoints for administrator. Required authorization")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AuthenticationService authenticationService;
    private final CategoryService categoryService;
    private final OfferService offerService;
    private final ProductService productService;
    private final OrderService orderService;
    private final TypeService typeService;

    @Operation(summary = "login for admin, open endpoint")
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto requestDto) {
        return authenticationService.authentication(requestDto);
    }

    @Operation(summary = "Create new product")
    @PostMapping("/products/new")
    public ProductResponseDto createNewProduct(
            @RequestBody @Valid ProductCreateRequestDto requestDto) {
        return productService.createProduct(requestDto);
    }

    @Operation(summary = "Update product info by product id")
    @PostMapping("/products/update/{id}")
    public ProductResponseDto updateProductInfo(
            @PathVariable Long id, @RequestBody @Valid ProductCreateRequestDto requestDto) {
        return productService.updateProduct(id, requestDto);
    }

    @Operation(summary = "Delete product by id",
            description = "Soft delete specify product")

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/products/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @Operation(summary = "Create new category")
    @PostMapping("/categories/new")
    public CategoryResponseDto createCategory(
            @RequestBody @Valid CategoryCreateRequestDto requestDto) {
        return categoryService.createCategory(requestDto);
    }

    @Operation(summary = "Update category by id")
    @PostMapping("/categories/update/{id}")
    public CategoryResponseDto updateCategory(
            @PathVariable Long id, @RequestBody CategoryCreateRequestDto requestDto) {
        return categoryService.updateCategory(id, requestDto);
    }

    @Operation(summary = "Delete category by id",
            description = "Soft delete specify category")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/categories/delete/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @Operation(summary = "Create new offer (service)")
    @PostMapping("/offers/new")
    public OfferResponseDto createOffer(
            @RequestBody @Valid OfferCreateRequestDto requestDto) {
        return offerService.createOffer(requestDto);
    }

    @Operation(summary = "Update offer by id")
    @PostMapping("offers/update/{id}")
    public OfferResponseDto updateOffer(
            @PathVariable Long id, @RequestBody @Valid OfferCreateRequestDto requestDto) {
        return offerService.updateOffer(id, requestDto);
    }

    @Operation(summary = "Delete offer by id",
            description = "Soft delete specify offer")
    @DeleteMapping("/offers/delete/{id}")
    public void deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/orders")
    public List<OrderResponseDto> getAllOrders(Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }

    @Operation(summary = "Get all not COMPETED orders")
    @GetMapping("/orders/active")
    public List<OrderResponseDto> getAllNotCompletedOrders(Pageable pageable) {
        return orderService.getAllNotCompletedOrders(pageable);
    }

    @Operation(summary = "Update specific order status")
    @PostMapping("/orders/{id}")
    public OrderResponseDto updateOrderStatus(
            @PathVariable Long id, @RequestBody OrderStatusUpdateRequestDto status) {
        return orderService.updateOrderStatus(id, status.orderStatus());
    }

    @Operation(summary = "Create new type for Offer (service)")
    @PostMapping("/types")
    public TypeResponseDto createType(TypeCreateRequestDto requestDto) {
        return typeService.createType(requestDto);
    }

    @DeleteMapping("types/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteType(@PathVariable Long id) {
        typeService.deleteType(id);
    }
}
