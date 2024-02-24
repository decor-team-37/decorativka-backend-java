package teamproject.decorativka.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import teamproject.decorativka.model.Order;

public record OrderDto(
        Long id,
        String firstName,
        String lastName,
        String patronymic,
        String shippingAddress,
        String email,
        String phoneNumber,
        Order.Status status,
        String comment,
        BigDecimal total,
        LocalDateTime orderDate,
        List<OrderItemDto> orderItems
) {
}
