package teamproject.decorativka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import teamproject.decorativka.dto.feedback.FeedbackRequestDto;
import teamproject.decorativka.service.FeedbackService;

@Tag(name = "Feedback manager",
        description = "endpoint for submit feedback")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Operation(summary = "Send feedback")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void submitFeedback(@RequestBody FeedbackRequestDto requestDto) {
        feedbackService.processFeedback(requestDto);
    }
}
