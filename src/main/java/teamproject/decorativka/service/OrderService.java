package teamproject.decorativka.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import teamproject.decorativka.dto.order.OrderCreateRequestDto;
import teamproject.decorativka.dto.order.OrderResponseDto;
import teamproject.decorativka.model.Order;

public interface OrderService {
    OrderResponseDto placeOrder(OrderCreateRequestDto requestDto);

    List<OrderResponseDto> getAllOrders(Pageable pageable);

    List<OrderResponseDto> getAllNotCompletedOrders(Pageable pageable);

    OrderResponseDto updateOrderStatus(Long id, Order.Status status);
}
