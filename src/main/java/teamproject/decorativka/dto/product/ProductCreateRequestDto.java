package teamproject.decorativka.dto.product;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record ProductCreateRequestDto(
        @NotNull
        String name,
        @NotNull
        Long categoryId,
        @NotNull
        BigDecimal price,
        String country,
        String producer,
        String collection,
        String type,
        String tone,
        String room,
        @NotNull
        String description,
        List<String> imageUrl
) {
}
