package teamproject.decorativka.dto.product;

import java.util.List;

public record ProductResponseDto(
        Long id,
        String name,
        Long categoryId,
        String country,
        String producer,
        String collection,
        String type,
        String room,
        String description,
        List<String> imageUrl
) {
}
