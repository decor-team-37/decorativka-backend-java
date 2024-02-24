package teamproject.decorativka.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import teamproject.decorativka.dto.order.OrderCreateRequestDto;
import teamproject.decorativka.dto.order.OrderItemCreateRequestDto;
import teamproject.decorativka.dto.order.OrderResponseDto;
import teamproject.decorativka.mapper.OrderMapper;
import teamproject.decorativka.model.Order;
import teamproject.decorativka.model.OrderItem;
import teamproject.decorativka.model.Product;
import teamproject.decorativka.repository.OrderRepository;
import teamproject.decorativka.service.OrderService;
import teamproject.decorativka.service.ProductService;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Override
    public OrderResponseDto placeOrder(OrderCreateRequestDto requestDto) {
        List<OrderItem> orderItems = createOrderItemFromDto(requestDto.orderItems());
        Order order = orderMapper.toModel(requestDto);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.Status.PENDING);
        order.setTotal(calculateTotal(orderItems));
        order.setOrderItems(new HashSet<>(orderItems));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders(Pageable pageable) {
        return orderRepository.getAllByDeletedFalse(pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderResponseDto> getAllNotCompletedOrders(Pageable pageable) {
        return getAllOrders(pageable).stream()
                .filter(o -> !o.status().equals(Order.Status.COMPLETED))
                .toList();
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long id, Order.Status status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderMapper.toDto(orderRepository.save(order));
    }

    private Order getOrderById(Long id) {
        return orderRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find order with id:" + id)
        );
    }

    private List<OrderItem> createOrderItemFromDto(List<OrderItemCreateRequestDto> orderItemsDtos) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemCreateRequestDto orderItemDto : orderItemsDtos) {
            OrderItem orderItem = new OrderItem();
            Product product = productService.getProduct(orderItemDto.productId());
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemDto.quantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private BigDecimal calculateTotal(List<OrderItem> orderItems) {
        BigDecimal total = new BigDecimal(0);
        for (OrderItem orderItem : orderItems) {
            total.add(orderItem.getPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }
        return total;
    }
}
