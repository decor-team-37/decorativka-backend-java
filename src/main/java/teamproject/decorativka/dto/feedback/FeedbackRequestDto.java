package teamproject.decorativka.dto.feedback;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record FeedbackRequestDto(
        @NotNull
        String name,
        @Email
        @NotNull
        String email,
        @NotNull
        String comment
) {
}
