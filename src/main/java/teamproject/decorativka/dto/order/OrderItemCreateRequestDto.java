package teamproject.decorativka.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemCreateRequestDto(
        @NotNull
        Long productId,
        @NotNull
        @Min(1)
        Integer quantity
) {
}
