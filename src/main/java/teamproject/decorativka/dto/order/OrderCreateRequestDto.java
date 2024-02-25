package teamproject.decorativka.dto.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderCreateRequestDto(
        @NotNull
        List<OrderItemCreateRequestDto> orderItems,
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        String patronymic,
        @NotNull
        String shippingAddress,
        @NotNull
        @Email
        String email,
        @NotNull
        String phoneNumber,
        String comment
) {
}
