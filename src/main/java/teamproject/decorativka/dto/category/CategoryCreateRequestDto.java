package teamproject.decorativka.dto.category;

import jakarta.validation.constraints.NotNull;

public record CategoryCreateRequestDto(
        @NotNull
        String name,
        @NotNull
        String description
) {
}
