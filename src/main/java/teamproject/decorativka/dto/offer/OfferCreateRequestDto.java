package teamproject.decorativka.dto.offer;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OfferCreateRequestDto(
        @NotNull
        String name,
        @NotNull
        String description,
        List<String> imageUrl
) {
}
