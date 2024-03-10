package teamproject.decorativka.service;

import teamproject.decorativka.dto.feedback.FeedbackRequestDto;
import teamproject.decorativka.model.Order;

public interface EmailContentService {
    String createOrderConfirmationContent(Order order);

    String createStatusUpdateContent(Order order, String newStatus);

    String createFeedbackEmailContend(FeedbackRequestDto requestDto);
}
