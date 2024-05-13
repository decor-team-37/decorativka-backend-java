package teamproject.decorativka.dto.offer;

import java.util.List;

public record OfferResponseDto(
        Long id,
        String name,
        String description,
        Long categoryId,
        List<String> imageUrl
) {
}
