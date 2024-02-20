package teamproject.decorativka.dto.product;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProductCreateRequestDto(
        @NotNull
        String name,
        @NotNull
        Long categoryId,
        String country,
        String producer,
        String collection,
        String type,
        String room,
        @NotNull
        String description,
        List<String> imageUrl
) {
}
