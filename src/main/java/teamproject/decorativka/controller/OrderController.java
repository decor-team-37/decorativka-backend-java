package teamproject.decorativka.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.decorativka.dto.order.OrderCreateRequestDto;
import teamproject.decorativka.dto.order.OrderResponseDto;
import teamproject.decorativka.service.OrderService;

@Tag(name = "Endpoint to create order")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public OrderResponseDto placeOrder(@RequestBody @Valid OrderCreateRequestDto requestDto) {
        return orderService.placeOrder(requestDto);
    }
}
