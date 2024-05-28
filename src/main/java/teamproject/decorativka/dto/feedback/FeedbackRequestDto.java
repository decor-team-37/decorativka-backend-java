package teamproject.decorativka.dto.feedback;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FeedbackRequestDto(
        @NotNull
        String name,
        @Email
        String email,
        @NotNull
        @Size(min = 10, max = 15)
        String phoneNumber,
        String comment
) {
}
