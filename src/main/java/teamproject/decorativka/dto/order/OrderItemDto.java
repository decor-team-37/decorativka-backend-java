package teamproject.decorativka.dto.order;

import java.math.BigDecimal;

public record OrderItemDto(
        Long id,
        Long productId,
        Integer quantity,
        BigDecimal price
) {
}
