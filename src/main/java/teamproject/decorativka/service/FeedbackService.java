package teamproject.decorativka.service;

import teamproject.decorativka.dto.feedback.FeedbackRequestDto;

public interface FeedbackService {
    void processFeedback(FeedbackRequestDto requestDto);
}
